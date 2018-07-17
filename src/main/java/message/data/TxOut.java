package message.data;

import util.ByteParser;

public class TxOut {

    private long value;
    private int pk_script_length;
    private String pk_script;

    public TxOut (ByteParser parser) {
        value = parser.parseLong(true);
        pk_script_length = (int)parser.parseVarInt().value();
        pk_script = parser.parseHexString(pk_script_length);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\n  < TxOut >");
        sb.append("\n  Value: ").append(value);
        sb.append("\n  Pk_script: ").append(pk_script);
        return sb.toString();
    }
}
