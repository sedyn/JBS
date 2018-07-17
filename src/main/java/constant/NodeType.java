package constant;

public enum NodeType {

    NODE_NETWORK(1),
    NODE_GETUTXO(1 << 1),
    NODE_BLOOM(1 << 2),
    NODE_WITNESS(1 << 3),
    NODE_XTHIN(1 << 4),
    NODE_BITCOIN_CASH(1 << 5);

    private final int value;

    NodeType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
