package connect;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import connect.data.NodeHashMap;
import connect.data.StateBundle;
import constant.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import message.data.IPv6;
import message.data.NetAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Lookup;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionManager {

    private EventLoopGroup worker;
    private final int nThreads = 8;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    public static final NodeHashMap map = new NodeHashMap();
    public static ConcurrentHashMap<String, Boolean> connection = new ConcurrentHashMap<>();
    public static final LinkedBlockingQueue<NetAddr> queue = new LinkedBlockingQueue<>();

    public ConnectionManager () {
        worker = new NioEventLoopGroup(nThreads);
    }

    @SuppressWarnings("unchecked")
    private void readyQueue () throws Exception{
        File file = new File("node.json");
        if(!file.exists()) {
            // if node.json is not exist, lookup seed.
            InetAddress[] address = Lookup.lookup(Constants.SEED_DNS[0]);
            if(address == null) {
                logger.info("Lookup seed fail");
                System.exit(0);
            }

            for(InetAddress addr : address) {
                connection.put(IPv6.convert(addr.getHostAddress()), false);
                queue.put(new NetAddr(0, 0, addr.getHostAddress(), 8333));
            }

            Constants.om.writerWithDefaultPrettyPrinter().writeValue(file, connection);
            return;
        }

        connection = Constants.om.readValue(file, ConcurrentHashMap.class);

        file = new File("queue.json");
        for (JsonNode node : Constants.om.readTree(file)) {
            long time = node.get("time").asLong();
            long services = node.get("services").asLong();
            IPv6 ip = new IPv6((node.get("ip").asText()));
            int port = node.get("port").asInt();
            queue.add(new NetAddr(time, services, ip, port));
        }
    }

    private static int count = 0;

    synchronized public static void done() {
        count++;
    }

    public void start2 (final int goal) throws Exception {
        Bootstrap bs = getNewBootstrap(worker);
        readyQueue();
        logger.info("Queue: " + queue.size());
        if(queue.size() == 0)
            System.exit(0);

        try {

            final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            while(count < goal) {
                while(channels.size() <= nThreads) {
                    try {
                        NetAddr target = queue.take();
                        if (!connection.getOrDefault(target.getIp(), false)) {
                            StateBundle bundle = new StateBundle(target.getIp(), target.getPort(), new Date().getTime());
                            ConnectionHandler.map.put(target.getIp().toString(), bundle);
                            connection.put(target.getIp().toString(), true);
                            ChannelFuture future = bs.connect(target.getIp().toString(), target.getPort());
                            channels.add(future.channel());
                        }
                    }catch (ClassCastException e1){
                        logger.warn(":::: Exception", e1);
                        count = goal;
                        break;
                    }catch (Exception e2) {
                        logger.warn(":::: Exception", e2);
                    }
                }
            }

            channels.close().sync();
        }finally {
            worker.shutdownGracefully().sync();
        }

        writeSet();
    }

    /*
    public void start1 (final int goal) throws Exception {
        Bootstrap bs = getNewBootstrap(worker);
        readyQueue();
        logger.info("Queue: " + queue.size());
        if(queue.size() == 0)
            System.exit(0);
        NetAddr target;
        try {
           for(;;) {
               try {
                   target = queue.take();
                   if(!connection.getOrDefault(target.getIp().toString(), false)) {
                       StateBundle bundle = new StateBundle(target.getIp().toString(), target.getPort(), new Date().getTime());
                       ConnectionHandler.map.put(target.getIp().toString(), bundle);
                       connection.put(target.getIp().toString(), true);
                       ChannelFuture future = bs.connect(target.getIp().toString(), target.getPort());
                       futureBundle.put(future, bundle);
                       futures.add(future);
                   }
               } catch (Exception e) {
                   logger.error("Error ::::", e);
               }

               if(futures.size() >= nThreads) {
                   for(ChannelFuture f : futures) {
                       if(f.cause() instanceof io.netty.channel.ConnectTimeoutException) {
                           StateBundle bundle = futureBundle.get(f);
                           bundle.setTimeout(true);
                           bundle.setException(f.cause());
                       }

                       if(!f.isDone())
                           f.channel().closeFuture().sync();

                       futures.remove(f);
                       if(f.isDone() && f.isSuccess()) {
                           count++;
                       }
                   }

                   logger.info("Count: " + count);
                   if(count > goal) {
                       break;
                   }
               }

               if(queue.isEmpty()) {
                   break;
               }
           }

        } finally {
            worker.shutdownGracefully();
        }

        logger.info("Queue: " + queue.size());
        logger.info("Map: " + map.size());
        logger.info("Connection: " + connection.size());

        for(String s : ConnectionHandler.map.keySet()) {
            if(ConnectionHandler.map.get(s).getVersion() == null) {
                connection.put(s, false);
            }
        }

        writeSet();
    }
    */

    private void writeSet() throws Exception {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        File file = new File("data" + File.separator + sdf.format(date) + "-node.json");
        Constants.om.writerWithDefaultPrettyPrinter().writeValue(file, map);
        logger.info("Map<String, NetAddr> Finish");
        file = new File("data" + File.separator + sdf.format(date) + "-bundle.json");
        Constants.om.writerWithDefaultPrettyPrinter().writeValue(file, ConnectionHandler.map);
        logger.info("Map<String, StateBundle> Finish");
        file = new File("node.json");
        Constants.om.writerWithDefaultPrettyPrinter().writeValue(file, connection);
        logger.info("Map<String, Boolean> Finish");
        file = new File("queue.json");
        Constants.om.writerWithDefaultPrettyPrinter().writeValue(file, queue);
        logger.info("Queue<NetAddr> Finish");
    }

    private Bootstrap getNewBootstrap (EventLoopGroup group) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MessageDecoder(), new ConnectionHandler());
            }
        });

        return bootstrap;
    }
}
