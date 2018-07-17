package constant;

import util.ByteUtils;

public enum CommandMap {

    VERSION("version"),
    VERACK("verack"),
    PING("ping"),
    PONG("pong"),
    GETADDR("getaddr"),
    ADDR("addr"),
    INV("inv"),
    ALERT("alert"),
    GETUTXOS("getutxos"),
    UTXOS("utxos"),
    TX("tx"),
    GETDATA("getdata"),
    REJECT("reject"),
    BLOCK("block");

    private final String command;
    public final int length;

    CommandMap (String command) {
        this.command = command;
        this.length = ByteUtils.stringToBytes(command).length;
    }

    @Override
    public String toString () {
        return command;
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[12];
        System.arraycopy(ByteUtils.stringToBytes(command), 0, bytes, 0, length);
        return bytes;
    }
}
