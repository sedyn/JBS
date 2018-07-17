package message;


import constant.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ByteBufParser;
import util.ByteParser;
import util.ByteUtils;
import util.Utils;

import java.nio.ByteBuffer;

public class Header {
    private int startString;
    private String commandName;
    private int payloadSize;
    private String RecvChecksum;
    private String CalcChecksum;
    private byte[] content;

    private final Logger logger = LoggerFactory.getLogger(Header.class);

    public Header (ByteBuffer buffer) {
        ByteParser parser = new ByteParser(buffer);
        startString = parser.parseInt(false);
        commandName = parser.parseCharString(12).trim();
        payloadSize = parser.parseInt(true);
        RecvChecksum = parser.parseHexString(4);
        content = parser.parseByte(payloadSize);
        CalcChecksum = ByteUtils.bytesToHexString(Utils.sha256Twice(content), 0, 4);
    }

    public Header (ByteBuf buf) {
        ByteBufParser parser = new ByteBufParser(buf);
        startString = parser.parseInt(false);
        commandName = parser.parseAsciiString(12).trim();
        payloadSize = parser.parseInt(true);
        RecvChecksum = parser.parseHexString(4);
        if(buf.readerIndex() + payloadSize <= buf.writerIndex()) {
            content = parser.parseByte(payloadSize);
            CalcChecksum = ByteBufUtil.hexDump(Utils.sha256Twice(content), 0, 4);
        } else {
            buf.readerIndex(buf.readerIndex() - Constants.DEFAULT_SIZE_OF_HEADER);
        }
    }

    public boolean isValid () {
         return isValidStartString() && isValidChecksum();
    }

    public boolean isValidStartString() {
        return startString == Constants.START_STRING;
    }

    public boolean isValidChecksum () {
        return RecvChecksum.equals(CalcChecksum);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Header{");
        sb.append("startString=").append(String.format("0x%x", startString));
        sb.append(", commandName='").append(commandName).append('\'');
        sb.append(", payloadSize=").append(payloadSize);
        sb.append(", RecvChecksum='").append(RecvChecksum).append('\'');
        sb.append(", CalcChecksum='").append(CalcChecksum).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getStartString() {
        return startString;
    }

    public String getCommandName() {
        return commandName;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public String getRecvChecksum() {
        return RecvChecksum;
    }

    public String getCalcChecksum() {
        return CalcChecksum;
    }

    public byte[] getContent() {
        return content;
    }
}
