package message;


import constant.CommandMap;
import util.ByteParser;
import util.ByteUtils;

import java.nio.ByteBuffer;

public class Reject extends Message implements Receivable{

    private int message_bytes;
    private String message;
    private byte code;
    private int reason_bytes;
    private String reason;
    private String extra_data;

    public Reject (ByteBuffer buffer) {
        super(CommandMap.REJECT);
        deserialize(buffer);
    }

    public Reject (Header header) {
        super(CommandMap.REJECT);
        deserialize(header);
    }

    @Override
    public void deserialize(ByteBuffer buffer) {
        header = new Header(buffer);
        ByteParser parser = new ByteParser(header.getContent());
        message_bytes = (int)parser.parseVarInt().value();
        message = parser.parseCharString(message_bytes);
        code = parser.parseChar();
        reason_bytes = (int)parser.parseVarInt().value();
        reason = parser.parseCharString(reason_bytes);
        extra_data = ByteUtils.bytesToHexString(parser.parseRemain());
    }

    private void deserialize(Header header) {
        this.header = header;
        ByteParser parser = new ByteParser(header.getContent());
        message_bytes = (int)parser.parseVarInt().value();
        message = parser.parseCharString(message_bytes);
        code = parser.parseChar();
        reason_bytes = (int)parser.parseVarInt().value();
        reason = parser.parseCharString(reason_bytes);
        extra_data = ByteUtils.bytesToHexString(parser.parseRemain());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reject{");
        sb.append("message_bytes=").append(message_bytes);
        sb.append(", message='").append(message).append('\'');
        sb.append(", code=").append(code);
        sb.append(", reason_bytes=").append(reason_bytes);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", extra_data='").append(extra_data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
