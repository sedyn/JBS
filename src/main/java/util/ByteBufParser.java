package util;

import exception.InvalidIpAddressException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import message.Header;
import message.data.IPv6;
import message.data.Inventory;
import message.data.NetAddr;
import message.data.VarInt;

import java.nio.charset.Charset;

public class ByteBufParser {

    private final ByteBuf buf;

    public ByteBufParser (ByteBuf buf) {
        this.buf = buf;
    }

    public char parseChar() {
        return buf.readChar();
    }

    public int parseUnsignedInt (boolean isLittleEndian) {
        return (int)(isLittleEndian ? buf.readUnsignedIntLE() : buf.readUnsignedInt());
    }

    public int parseInt (boolean isLittleEndian) {
        return (isLittleEndian ? buf.readIntLE() : buf.readInt());
    }

    public long parseLong (boolean isLittleEndian) {
        return (isLittleEndian ? buf.readLongLE() : buf.readLong());
    }

    public String parseAsciiString (int length) {
        return buf.readCharSequence(length, Charset.forName("US-ASCII")).toString();
    }

    public String parseHexString (int length) {
        return ByteBufUtil.hexDump(buf.readSlice(length));
    }

    public byte[] parseByte (int length) {
        byte[] sub = new byte[length];
        buf.readBytes(sub);
        return sub;
    }

    public Header parseHeader () {
        return new Header(buf);
    }

    public VarInt parseVarInt () {
        return new VarInt(buf);
    }

    public int parsePort () {
        return (int) buf.readChar();
    }

    public IPv6 parseIPv6 () {
        StringBuilder sb = new StringBuilder(39);
        byte[] sub = new byte[16];
        buf.readBytes(sub);
        String[] address = Utils.splitByLength(ByteBufUtil.hexDump(sub), 4);
        for(int i = 0; i < address.length; i++){
            sb.append(address[i]);
            if(i != address.length - 1)
                sb.append(":");
        }

        return new IPv6(sb.toString());
    }

    public NetAddr parseNetAddr () {
        byte[] sub = new byte[30];
        buf.readBytes(sub);
        return new NetAddr(sub);
    }

    public Inventory parseInventory () {
        byte[] sub = new byte[36];
        buf.readBytes(sub);
        return new Inventory(sub);
    }

}
