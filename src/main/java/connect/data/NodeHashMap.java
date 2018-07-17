package connect.data;

import message.data.NetAddr;

import java.util.concurrent.ConcurrentHashMap;

public class NodeHashMap {

    private final ConcurrentHashMap<String, Node> map;
    private int duplicated;

    public NodeHashMap () {
        duplicated = 0;
        map = new ConcurrentHashMap<>();
    }

    synchronized public void insert (NetAddr netAddr, StateBundle bundle) {
        String ip = netAddr.getIp().toString();

        if(!map.containsKey(ip)) {
            map.put(ip, new Node(netAddr, bundle));
        } else {
            duplicated++;
            Node node = map.get(ip);
            node.add(netAddr, bundle);
            node.duplicated();
        }
    }

    public int size () {
        return map.size();
    }

    public void clear () {
        map.clear();
    }

    public ConcurrentHashMap<String, Node> getMap () {
        return map;
    }

    public int getDuplicated() {
        return duplicated;
    }
}
