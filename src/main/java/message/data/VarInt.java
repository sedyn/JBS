package message.data;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import util.ByteUtils;

public class VarInt {

    private final long value;
    private int length;

    public VarInt (byte[] pBytes, int position) {
        int follow = (pBytes[position] & 0xff) - 0xfd;
        length = (follow >= 0 ? 2 << follow : 1);
        byte[] bytes = ByteUtils.subBytes(pBytes, position + (follow >= 0 ? 1 : 0), length);
        value = Long.parseLong(ByteBufUtil.hexDump(ByteUtils.reverseBytes(bytes)), 16);
        length += (follow >= 0 ? 1 : 0);
    }

    public VarInt (ByteBuf buf) {
        int follow = buf.readUnsignedByte() - 0xfd;
        length = (follow >= 0 ? 2 << follow : 1);
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        value = Long.parseLong(ByteBufUtil.hexDump(ByteUtils.reverseBytes(bytes)), 16);
    }

    public long value () {
        return value;
    }

    public int length () {
        return length;
    }
}
