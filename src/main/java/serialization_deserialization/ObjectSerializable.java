package serialization_deserialization;

import java.nio.ByteBuffer;

public interface ObjectSerializable {
    void serialize( ByteBuffer stream );
}
