package message;

import java.nio.ByteBuffer;

public interface Receivable {
   void deserialize(ByteBuffer buffer);
}
