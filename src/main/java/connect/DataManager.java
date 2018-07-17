package connect;

import connect.data.NodeHashMap;
import connect.data.StateBundle;
import io.netty.channel.ChannelFuture;
import message.data.NetAddr;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataManager {
    /*
     * NodeHashMap
     * ConcurrentHashMap<String, Boolean>
     * ConcurrentHashMap<ChannelFuture, StateBundle>
     * ConcurrentHashMAp
     */

    public static final NodeHashMap map = new NodeHashMap();
    public static ConcurrentHashMap<String, Boolean> connection = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<ChannelFuture> futures = new ConcurrentLinkedQueue<>();;
    private static final ConcurrentHashMap<ChannelFuture, StateBundle> channelFutureBundle = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, StateBundle> stringBundle = new ConcurrentHashMap<>();

    public DataManager () {

    }

    synchronized public static StateBundle getStateBundle (ChannelFuture future) {
        return channelFutureBundle.get(future);
    }

    synchronized public static StateBundle getStateBundle (String ip) {
        return stringBundle.get(ip);
    }
}
