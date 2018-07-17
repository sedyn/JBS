package message.data;

import constant.Constants;
import constant.InvType;
import util.ByteParser;
import util.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Inventory {

    private InvType type;
    private String hash;

    public Inventory(byte[] bytes) {
        ByteParser parser = new ByteParser(bytes);

        int type = parser.parseInt(true);
        if(type <= InvType.MSG_CMPCT_BLOCK.value) {
            this.type = Constants.INV_TYPES[type];
        } else {
            if(type == InvType.MSG_WITNESS_BLOCK.value)
                this.type = InvType.MSG_WITNESS_BLOCK;
            else if(type == InvType.MSG_WITNESS_TX.value)
                this.type = InvType.MSG_WITNESS_TX;
            else if(type == InvType.MSG_FILTERED_WITNESS_BLOCK.value)
                this.type = InvType.MSG_FILTERED_WITNESS_BLOCK;
        }

        hash = ByteUtils.bytesToHexString(ByteUtils.reverseBytes(parser.parseByte(32)));
    }

    public byte[] serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(36);
        buffer.order(ByteOrder.LITTLE_ENDIAN).putInt(type.value).order(ByteOrder.BIG_ENDIAN).put(ByteUtils.hexStringToBytes(hash));
        return buffer.array();
    }

    public InvType getType () {
        return type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Inventory{");
        sb.append("data=").append(type);
        sb.append(", hash='").append(hash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
