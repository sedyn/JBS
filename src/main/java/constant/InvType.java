package constant;

public enum InvType {

    ERROR(0),
    MSG_TX(1),
    MSG_BLOCK(2),
    MSG_FILTERED_BLOCK(3),
    MSG_CMPCT_BLOCK(4),
    MSG_WITNESS_BLOCK(MSG_BLOCK.value | Constants.MSG_WITNESS_FLAG),
    MSG_WITNESS_TX(MSG_TX.value | Constants.MSG_WITNESS_FLAG),
    MSG_FILTERED_WITNESS_BLOCK(MSG_FILTERED_BLOCK.value | Constants.MSG_WITNESS_FLAG);

    public final int value;

    InvType(int value) {
        this.value = value;
    }
}
