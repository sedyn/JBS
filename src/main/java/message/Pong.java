package message;


import constant.CommandMap;
import io.netty.buffer.ByteBufUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Pong extends Message implements Sendable, Receivable{

    private long nonce;

    public Pong(ByteBuffer buffer) {
        super(CommandMap.PONG);
        deserialize(buffer);
    }

    public Pong(long nonce) {
        super(CommandMap.PONG);
        this.nonce = nonce;
    }

    public Pong(Header header) {
        super(CommandMap.PONG);
        deserialize(header);
    }

    @Override
    public ByteBuffer serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN).putLong(nonce);
        return insertHeader(buffer);
    }

    @Override
    public void deserialize(ByteBuffer buffer) {
        this.nonce = buffer.order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    private void deserialize(Header header) {
        this.header = header;
        this.nonce = Long.parseLong(ByteBufUtil.hexDump(header.getContent()), 16);
    }

    @Override
    public CommandMap getMessageName() {
        return command;
    }

    public long getNonce() {
        return nonce;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pong{");
        sb.append("nonce=").append(String.format("0x%x", nonce));
        sb.append('}');
        return sb.toString();
    }
}
