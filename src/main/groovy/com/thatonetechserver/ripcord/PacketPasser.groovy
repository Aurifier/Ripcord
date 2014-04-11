package com.thatonetechserver.ripcord

/**
 * Created by Aurifier on 4/5/14.
 */
class PacketPasser {
    private Socket clientSock
    public PacketPasser(Socket listenSocket) {
        clientSock = listenSocket
    }

    public start(Socket serverSock) {
        serverSock.getOutputStream().newWriter().write "Hello World!"
    }
}
