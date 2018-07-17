package util;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MessageConcatter {

    private int hash;
    private int payload;
    private int filled;
    private String command;
    private String recvChecksum;
    private byte[] header;
    private ArrayList<byte[]> contents;
    private int index;

    public MessageConcatter(ByteBuffer header ,String recvChecksum, String command, int payload, int recvSize) {
        this.header = new byte[recvSize];
        header.rewind();
        header.get(this.header, 0, recvSize);
        this.command = command;
        this.recvChecksum = recvChecksum;
        this.payload = payload;
        this.contents = new ArrayList<>(4);
        this.index = 0;
        this.filled = recvSize;
        hash = (int)Long.parseLong(recvChecksum, 16);
    }

    public void addContent (ByteBuffer content, int size) {
        contents.add(new byte[size]);
        content.rewind();
        content.get(contents.get(index++), 0, size);
        filled += size;
    }
/*
    public Message concat () {
        Message message = null;
        ByteBuffer buffer = ByteBuffer.allocate(filled);
        buffer.put(header);
        for(int i = 0; i < index; i++) buffer.put(contents.get(i));
        buffer.rewind();
        switch (command) {
            case "version":
                message = new Version(buffer);
                break;
            case "addr":
                message = new Addr(buffer);
                break;
            case "ping":
                message = new Ping(buffer);
                break;
            case "inv":
                message = new Inv(buffer);
                break;
            case "alert":
                message = new Alert(buffer);
                break;
            case "utxos":
                message = new Utxos(buffer);
                break;
            case "tx":
                message = new Tx(buffer);
                break;
            case "reject":
                message = new Reject(buffer);
                break;
            case "pong":
                message = new Pong(buffer);
                break;
            case "block":
                message = new Block(buffer);
                break;
        }

        return message;
    }
*/
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageConcatter{");
        sb.append("payload=").append(payload);
        sb.append(", filled=").append(filled);
        sb.append(", commandName='").append(command).append('\'');
        sb.append(", recvChecksum='").append(recvChecksum).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getCommandName() {
        return command;
    }

    public String getRecvChecksum() {
        return recvChecksum;
    }

    public int getFilled() {
        return filled;
    }

    public int getPayload() {
        return payload;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
