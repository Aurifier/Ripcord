/**
 * Created by Aurifier on 4/5/14.
 */
def mcHost = "play.thatonetechserver.com"
def mcPort = 25565
def listenSocket = new ServerSocket(25565)

while(true) {
    listenSocket.accept { clientSock ->
        println("Client connected.")
        def mcSocket = new Socket(mcHost, mcPort)
        mcSocket.withStreams { mcInputStream, mcOutputStream ->
            def mcReader = mcInputStream.newReader()
            def mcWriter = mcOutputStream
            clientSock.withStreams { clientInputStream, clientOutputStream ->
                def clientReader = clientInputStream.newReader()
                def clientWriter = clientOutputStream
                while(!clientSock.isClosed()) {
                    if(clientReader.ready()) {
                        mcWriter.write(clientReader.read())
                    }

                    if(mcReader.ready()) {
                        clientWriter.write(mcReader.read())
                    }
                }
            }
            mcSocket.close()
        }
    }
}
