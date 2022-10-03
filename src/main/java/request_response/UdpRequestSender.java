package request_response;

import commands.CommandId;
import exceptions.SocketIoException;
import serialization_deserialization.ObjectSerializable;
import serialization_deserialization.ObjectSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class UdpRequestSender implements AutoCloseable {
    private InetAddress inetAddressToSend;
    private int ipPortToSend;
    private DatagramSocket socket;
    private int packetSizeInBytes;

    @Override
    public void close() throws Exception {
        socket.close();
    }

    public UdpRequestSender(String ipAddress, int port, int packetSize )
    {
        ipPortToSend = port;
        packetSizeInBytes = packetSize;

        try {
            inetAddressToSend = InetAddress.getByName(ipAddress);
            socket = new DatagramSocket();
        }catch (SocketException| UnknownHostException e ) {
            e.printStackTrace();
        }
    }

    private <T extends ObjectSerializable> void send(T cmd)
    {
        var serializer = new ObjectSerializer<T>(packetSizeInBytes);
        byte[] bytesToSend = serializer.serialize(cmd).array();
        DatagramPacket packet
                = new DatagramPacket(bytesToSend,
                bytesToSend.length,
                inetAddressToSend,
                ipPortToSend);

        try {
            socket.send(packet);
        }catch (IOException e) {
            throw new SocketIoException(e.getMessage());
        }
    }

    public <T extends ObjectSerializable> Future<Void> asyncSend( T cmd )
    {
        return CompletableFuture.runAsync(() -> { send(cmd); });
    }
}
