package request_response;

import commands.CommandId;
import exceptions.SocketIoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class UdpResponseReceiverTest {
    private UdpResponseReceiver receiver;
    private UdpRequestSender sender;

    @BeforeEach
    public void prepareTestCase()
    {
        receiver = new UdpResponseReceiver("127.0.0.1", 20000, 64);
        sender = new UdpRequestSender("127.0.0.1", 20000, 64);
    }

    @AfterEach
    public void cleanUpTestCase() throws Exception
    {
        sender.close();
        receiver.close();
    }

    @Test
    void commandIdSendReceiveTest() throws Exception {
        var commandId = new CommandId(10);

        var receivedCommand = new CommandId();

        var asyncReceiver = receiver.asyncReceive(receivedCommand);

        var asyncSender = sender.asyncSend(commandId);

        asyncReceiver.get();

        assertTrue(commandId.getCmdId() == receivedCommand.getCmdId());
    }

    @Test
    public void commandRxTimeoutExceptionTest()
    {
        var receivedCommand = new CommandId();

        var rxFuture = receiver.asyncReceive(receivedCommand);

        assertThrows(ExecutionException.class, () -> {rxFuture.get();} );
    }
}