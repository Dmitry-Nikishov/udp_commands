package commands;

import serialization_deserialization.ObjectDeserializable;
import serialization_deserialization.ObjectSerializable;

import java.nio.ByteBuffer;

public class ResponseHeader implements ObjectDeserializable<ResponseHeader> {
    private CommandId cmdId;
    private ErrorCode errorCode;

    public ResponseHeader()
    {
        cmdId = new CommandId(0);
        errorCode = new ErrorCode(0);
    }

    public ResponseHeader(CommandId id, ErrorCode errCode )
    {
        cmdId = id;
        errorCode = errCode;
    }

    @Override
    public ResponseHeader deserialize(ByteBuffer buf) {
        cmdId = new CommandId(buf.get(0));
        errorCode = new ErrorCode(buf.get(1));
        return this;
    }
}
