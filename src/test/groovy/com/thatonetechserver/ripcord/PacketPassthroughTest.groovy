import groovy.mock.interceptor.MockFor

/**
 * Created by Aurifier on 4/3/14.
 * A test for just passing packets through.
 */

class PacketPassthroughTest extends GroovyTestCase {
    void testClientPacketIsForwardedToServer() {
        def mockMinecraftServerSockContext = MockFor(Socket.class);
        /*I'm too tired to finish this, but basically I want to mock out the Streams and their Readers/Writers
        (as appropriate), then check that the data from the listen socket's InputStream shows up in the server socket's
        OutputStream.*/

        /*As an aside, it looks like we should be able to mock everything (including the sockets) with
        closures or maps of closures, which looks simpler than using MockFor for six different objects.*/

        Socket minecraftServerSock = mockMinecraftServerSockContext.proxyInstance();
        def mockListenSockContext = MockFor(Socket.class);

        PacketPasser passer = new PacketPasser(minecraftServerSock);
    }

    /* Getting ahead of myself
    void testNewlyJoinedClientIsStored() {
        Socket serverSock = MockFor(Socket.class);
        PacketPasser passer = new PacketPasser(serverSock);

        Socket listenSock = MockFor(Socket.class);
        String username = "ThatOneTechPerson";

        passer.
    }
    */
}

