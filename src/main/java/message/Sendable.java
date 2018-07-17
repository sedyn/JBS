package message;

import constant.CommandMap;

import java.nio.ByteBuffer;

public interface Sendable {
    ByteBuffer serialize();
    CommandMap getMessageName();
}
