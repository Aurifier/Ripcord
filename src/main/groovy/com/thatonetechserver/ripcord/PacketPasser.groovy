package com.thatonetechserver.ripcord

/**
 * Created by Aurifier on 4/5/14.
 */
class PacketPasser {
    private Socket clientSock
    private static final MAX_PACKET_LEN = 12

    public PacketPasser(Socket listenSocket) {
        clientSock = listenSocket
    }

    public start(Socket serverSock) {
        def writer = serverSock.getOutputStream().newWriter()
        def reader = clientSock.getInputStream().newReader()

        def buffer = new char[MAX_PACKET_LEN]
        reader.read(buffer, 0, MAX_PACKET_LEN)

        writer.write(buffer)
    }
}
