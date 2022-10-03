package serialization_deserialization;

import exceptions.ObjectExceedSerializableBufferException;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ObjectSerializer<T extends ObjectSerializable> {
    private int bufferSizeInBytes;

    public ObjectSerializer( int bufferSize )
    {
        bufferSizeInBytes = bufferSize;
    }

    public ByteBuffer serialize( T objectToSerialize )
    {
        var serializedBuffer = ByteBuffer.allocate(bufferSizeInBytes);

        objectToSerialize.serialize(serializedBuffer);

        if ( serializedBuffer.array().length > bufferSizeInBytes ) {
            throw new ObjectExceedSerializableBufferException( String.format("Buffer for serialization exceeded : %d vs %d",
                    serializedBuffer.array().length,
                    bufferSizeInBytes ) );
        }

        return serializedBuffer;
    }
}
