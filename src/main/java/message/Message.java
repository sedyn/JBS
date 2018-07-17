package message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constant.CommandMap;
import constant.Constants;
import util.ByteUtils;
import util.Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Message {

    protected final CommandMap command;
    protected Header header;

    protected Message (CommandMap command) {
        this.command = command;
    }

    public Message (Header header) {
        this.command = CommandMap.valueOf(header.getCommandName().toUpperCase());
        this.header = header;
    }

    protected ByteBuffer insertHeader (ByteBuffer content) {
        int size = 0;
        if(content != null)
            size += content.capacity();
        ByteBuffer message = ByteBuffer.allocate(Constants.DEFAULT_SIZE_OF_HEADER + size);
        message.putInt(Constants.START_STRING)
                .put(command.toByteArray())
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(content != null ? content.limit() : 0)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(content != null ? (int) Long.parseLong(ByteUtils.bytesToHexString(Utils.sha256Twice(content.array()), 0, 4) , 16) : Constants.EMPTY_HASH);
        if(content != null)
            message.put(content.array());
        message.rewind();
        return message;
    }

    @JsonIgnore
    public Header getHeader () {
        return header;
    }

    @JsonIgnore
    public CommandMap getCommand () {
        return command;
    }
}
