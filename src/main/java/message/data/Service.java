package message.data;

import constant.Constants;
import constant.NodeType;

import java.util.ArrayList;

public class Service {
    private long value;
    private ArrayList<NodeType> types;

    public Service(long value) {
        this.value = value;
        types = new ArrayList<>(Constants.NODE_TYPES.length);
        for (int i = 0; i < Constants.NODE_TYPES.length; i++) {
            if ((value & 0x01) == 0x01)
                types.add(Constants.NODE_TYPES[i]);
            value = value >> 1;
        }
    }

    public String toString() {
        if (this.value == 0) return "NODE_UNNAMED";
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i) != null)
                sb.append(types.get(i));
            if (i != types.size() - 1)
                sb.append("/");
        }
        return sb.toString();
    }
}
