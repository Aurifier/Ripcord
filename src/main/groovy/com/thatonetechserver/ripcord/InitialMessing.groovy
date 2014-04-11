/**
 * Created by drew on 4/3/14.
 */
def server = new ServerSocket(25565)

while(true) {
    server.accept { socket ->
        println "processing new connection..."
        socket.withStreams { input, output ->
            def reader = input.newReader()
            def packetId = reader.read()
            if(packetId == 2) {
                readPlayerProtocol(reader, packetId)
            }
        }
        println "processing/thread complete."
    }
}

private void readPlayerProtocol(BufferedReader reader, int packetId) {
    def clientId = reader.read()
    reader.read()
    def playerLength = reader.read()
    reader.read()

    def clientName = ""

    for (i in (1..playerLength)) {
        clientName += (char) reader.read()
        reader.read()

    }

    def serverLength = reader.read()
    def serverName = ""
    reader.read()
    for (i in (1..serverLength)) {
        serverName += (char) reader.read()
        reader.read()
    }

    println "packetId: $packetId, clientId: $clientId, playerName: $clientName server: $serverName"
}