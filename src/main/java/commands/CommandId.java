package commands;

import serialization_deserialization.ObjectDeserializable;
import serialization_deserialization.ObjectSerializable;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

public class CommandId implements ObjectSerializable, ObjectDeserializable<CommandId>
{
    private byte cmdId;

    public CommandId()
    {
        cmdId = 0;
    }

    public CommandId( int id)
    {
        cmdId = (byte) id;
    }

    @Override
    public void serialize(ByteBuffer buf) {
        buf.put(cmdId);
    }

    @Override
    public CommandId deserialize(ByteBuffer buf)
    {
        cmdId = buf.get(0);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandId commandId = (CommandId) o;
        return cmdId == commandId.cmdId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cmdId);
    }

    public byte getCmdId() {
        return cmdId;
    }
}
