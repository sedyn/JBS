package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import connect.data.Node;
import connect.data.NodeHashMap;

import java.io.IOException;
import java.util.Map;

public class NodeHashMapSerializer extends StdSerializer<NodeHashMap> {

    public NodeHashMapSerializer() {
        this(null);
    }

    public NodeHashMapSerializer(Class<NodeHashMap> c) {
        super(c);
    }

    @Override
    public void serialize(NodeHashMap n, JsonGenerator j, SerializerProvider s) throws IOException {
        j.writeStartObject();
        j.writeNumberField("duplicated", n.getDuplicated());
        j.writeNumberField("size", n.getMap().size());
        j.writeFieldName("node");
        j.writeStartArray();
        for(Map.Entry<String, Node> elem : n.getMap().entrySet()) {
            j.writeStartObject();
            j.writeStringField("ip", elem.getKey());
            j.writeNumberField("port", elem.getValue().getList().get(0).getPort());
            j.writeNumberField("duplicated", elem.getValue().getDuplicated());
            j.writeNumberField("count", elem.getValue().getList().size());
            j.writeObjectField("netaddr", elem.getValue().getList());
            j.writeObjectField("tag", elem.getValue().getTag());
            j.writeEndObject();
        }
        j.writeEndArray();
        j.writeEndObject();
    }
}
