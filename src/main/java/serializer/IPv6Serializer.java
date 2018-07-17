package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import message.data.IPv6;

import java.io.IOException;

public class IPv6Serializer extends StdSerializer<IPv6> {

    public IPv6Serializer() {
        this(null);
    }

    public IPv6Serializer(Class<IPv6> c) {
        super(c);
    }

    @Override
    public void serialize(IPv6 ip, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(ip.toString());
    }
}
