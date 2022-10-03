package serialization_deserialization;

import commands.CommandId;
import commands.RequestHeader;
import commands.ResponseHeader;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ObjectSerializerTest {

    @Test
    void commandIdSerializeTest() {
        final int cmdIdentifier = 10;
        var cmdId = new CommandId(cmdIdentifier);
        var objSerializer = new ObjectSerializer<CommandId>(16);
        var serializedData = objSerializer.serialize( cmdId );

        assertTrue( Arrays.equals(ByteBuffer.allocate(16).put((byte)cmdIdentifier).array(),serializedData.array()) );
    }

    @Test
    void requestHeaderSerializeTest()
    {
        final int cmdIdentifier = 10;
        final int serializedBufferSize = 16;

        var cmdId = new CommandId(cmdIdentifier);
        var requestHeader = new RequestHeader(cmdId);
        var objSerializer = new ObjectSerializer<RequestHeader>(serializedBufferSize);
        var serializedBuffer = objSerializer.serialize(requestHeader);

        var expectedBuffer = ByteBuffer.allocate(serializedBufferSize);
        expectedBuffer.put(ByteBuffer.allocate(2).array());
        expectedBuffer.put((byte)cmdIdentifier);

        assertTrue(Arrays.equals(expectedBuffer.array(),
                serializedBuffer.array()));
    }

    @Test
    void commandIdSerializerDeserializerTest()
    {
        final int cmdIdentifier = 10;
        var cmdId = new CommandId(cmdIdentifier);
        var objSerializer = new ObjectSerializer<CommandId>(16);
        var serializedData = objSerializer.serialize( cmdId );

        CommandId cmdToDeserialize = new CommandId();
        cmdToDeserialize.deserialize(serializedData);

        assertTrue(cmdToDeserialize.equals(cmdId));
    }

}