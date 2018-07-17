package message.data;

import util.ByteParser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NetAddr {
    private long time;
    private long services;
    private IPv6 ip;
    private int port;

    public NetAddr(byte[] bytes) {
        ByteParser parser = new ByteParser(bytes);
        time = parser.parseInt(true);
        services = parser.parseLong(true);
        ip = parser.parseIPv6();
        port = parser.parsePort();
    }

    public NetAddr(long time, long services, IPv6 ip, int port) {
        this.time = time;
        this.services = services;
        this.ip = ip;
        this.port = port;
    }

    public NetAddr(long time, long services, String ip, int port) throws Exception{
        this(time, services, new IPv6(ip), port);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetAddr{");
        sb.append("time=").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000)));
        sb.append(", services=").append(new Service(services));
        sb.append(", ip=").append(ip);
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }

    public InetAddress asInetAddress () throws UnknownHostException{
        return InetAddress.getByName(ip.toString());
    }

    public long getTime() {
        return time;
    }

    public long getServices() {
        return services;
    }

    public IPv6 getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
