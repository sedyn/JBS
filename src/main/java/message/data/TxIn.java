package message.data;


import util.ByteParser;

public class TxIn {

    private OutPoint previous_output;
    private int script_length;
    private String signature_script;
    private int sequence;

    public TxIn(ByteParser parser) {
        previous_output = new OutPoint(parser.parseByte(36));
        script_length = (int)parser.parseVarInt().value();
        signature_script = parser.parseHexString(script_length);
        sequence = parser.parseInt(true);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\n  < TxIn >");
        sb.append("\n  previous_output=").append(previous_output);
        sb.append("\n  script_length=").append(script_length);
        sb.append("\n  signature_script='").append(signature_script).append('\'');
        sb.append("\n  sequence=").append(sequence);
        return sb.toString();
    }
}
