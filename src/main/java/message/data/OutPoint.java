package message.data;

import util.ByteParser;
import util.ByteUtils;

import java.nio.ByteBuffer;

public class OutPoint {

    private String hash;
    private int index;

    public OutPoint(String hash, int index) {
        this.hash = hash;
        this.index = index;
    }

    public OutPoint(byte[] bytes) {
        ByteParser parser = new ByteParser(bytes);
        hash = parser.parseHexString(32);
        index = parser.parseInt(true);
    }

    public byte[] serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(36);
        buffer.put(ByteUtils.stringToBytes(hash));
        buffer.putInt(index);
        return buffer.array();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("< OutPoint >\n");
        sb.append("  Hash: ").append(hash);
        sb.append("\n  Index: ").append(index);
        return sb.toString();
    }
}
