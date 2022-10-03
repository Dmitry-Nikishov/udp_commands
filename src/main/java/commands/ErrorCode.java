package commands;

import serialization_deserialization.ObjectDeserializable;

import java.nio.ByteBuffer;

public class ErrorCode implements ObjectDeserializable<ErrorCode> {
    private byte errCode;

    public ErrorCode( int code )
    {
        errCode = (byte) code;
    }

    @Override
    public ErrorCode deserialize(ByteBuffer buf)
    {
        errCode = buf.get();
        return this;
    }
}
