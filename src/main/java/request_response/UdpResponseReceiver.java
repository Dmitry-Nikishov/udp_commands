package request_response;

import exceptions.SocketIoException;
import serialization_deserialization.ObjectDeserializable;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class UdpResponseReceiver implements AutoCloseable {
    private DatagramSocket socket;
    private byte[] packet;
    private ByteBuffer packetBuffer;

    public UdpResponseReceiver(String ipAddress, int port, int packetSize )
    {
        try {
            socket = new DatagramSocket(null);
            socket.bind(new InetSocketAddress(ipAddress, port));
            socket.setSoTimeout(1000);
            packet = new byte[packetSize];
            packetBuffer = ByteBuffer.wrap(packet);
        }catch (SocketException e){
            e.printStackTrace();
        }
    }

    private <T extends ObjectDeserializable> void receive(T cmd)
    {
        try {
            DatagramPacket datagram = new DatagramPacket(packet, packet.length);
            socket.receive(datagram);
            cmd.deserialize(packetBuffer);
        }catch (IOException e){
            throw new SocketIoException(e.getMessage());
        }
    }

    public <T extends ObjectDeserializable> Future<Void> asyncReceive(T cmd)
    {
        return CompletableFuture.runAsync(() -> {receive(cmd);} );
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
