package commands;

import serialization_deserialization.ObjectSerializable;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class RequestHeader implements ObjectSerializable {
    private byte[] dummyBytes;
    private CommandId cmdId;

    public RequestHeader( CommandId cmd )
    {
        dummyBytes = ByteBuffer.allocate(2).array();
        Arrays.fill(dummyBytes, (byte) 0);

        cmdId = cmd;
    }

    @Override
    public void serialize(ByteBuffer stream) {
        stream.put(dummyBytes);
        cmdId.serialize(stream);
    }
}
