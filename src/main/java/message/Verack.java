package message;


import constant.CommandMap;

import java.nio.ByteBuffer;

public class Verack extends Message implements Sendable, Receivable {

    public Verack() {
        super(CommandMap.VERACK);
    }

    public Verack(ByteBuffer buffer) {
        this();
        deserialize(buffer);
    }

    @Override
    public ByteBuffer serialize() {
        return insertHeader(null);
    }

    @Override
    public void deserialize(ByteBuffer buffer) {
        header = new Header(buffer);
    }

    @Override
    public CommandMap getMessageName() {
        return command;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Verack{");
        sb.append("header=").append(header);
        sb.append('}');
        return sb.toString();
    }
}
