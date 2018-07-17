package message;


import constant.CommandMap;

import java.nio.ByteBuffer;

public class GetAddr extends Message implements Sendable {

    public GetAddr() {
        super(CommandMap.GETADDR);
    }

    @Override
    public ByteBuffer serialize() {
        return insertHeader(null);
    }

    @Override
    public CommandMap getMessageName() {
        return command;
    }
}
