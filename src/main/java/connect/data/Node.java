package connect.data;

import message.data.NetAddr;

import java.util.LinkedList;

public class Node {

    private final LinkedList<NetAddr> list;
    private final LinkedList<Tag> tag;
    private int duplicated;

    public Node (NetAddr netAddr, StateBundle bundle) {
        duplicated = 0;
        list = new LinkedList<>();
        tag = new LinkedList<>();
        list.add(netAddr);
        tag.add(new Tag(bundle.getIp(), bundle.getConnectionTry()));
    }

    public void add (NetAddr netAddr, StateBundle bundle) {
        list.add(netAddr);
        tag.add(new Tag(bundle.getIp(), bundle.getConnectionTry()));
    }

    public void duplicated () {
        duplicated++;
    }

    public LinkedList<NetAddr> getList() {
        return list;
    }

    public LinkedList<Tag> getTag() {
        return tag;
    }

    public int getDuplicated() {
        return duplicated;
    }
}
