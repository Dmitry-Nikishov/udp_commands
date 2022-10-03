package serialization_deserialization;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public interface ObjectDeserializable<T> {
    T deserialize(ByteBuffer buf);
}
