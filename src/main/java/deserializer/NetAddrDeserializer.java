package deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import message.data.IPv6;
import message.data.NetAddr;

import java.io.IOException;

public class NetAddrDeserializer extends StdDeserializer<NetAddr>{

    public NetAddrDeserializer() {
        this(null);
    }

    public NetAddrDeserializer(Class<NetAddr> c) {
        super(c);
    }

    @Override
    public NetAddr deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        long time = (Long) (node.get("time")).numberValue();
        long services = (Long) (node.get("services").numberValue());
        IPv6 ip = new IPv6((node.get("ip").asText()));
        int port = (Integer) (node.get("port").numberValue());

        return new NetAddr(time, services, ip, port);
    }
}
