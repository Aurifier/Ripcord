package com.thatonetechserver.ripcord

import groovy.mock.interceptor.MockFor
import org.mockito.Mock
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import spock.lang.Specification
import static org.mockito.Mockito.*

/**
 * Created by Aurifier on 4/3/14.
 * A test for just passing packets through.
 */

class PacketPassthroughSpec extends Specification {
    def "client packet is forwarded to server"() {
    given: "a socket to a client and a socket to a minecraft server"
        def sentdata = ""
        def mockMinecraftServerWriter = [write: {param -> sentdata = param}] as Writer
        def mockMinecraftServerOutputStream = mock(OutputStream.class)
        def mockMinecraftServerSock = [getOutputStream: mockMinecraftServerOutputStream] as Socket

        mockMinecraftServerOutputStream.metaClass.newWriter = {
            mockMinecraftServerWriter
        }

        def mockListenReader = [read: {chars, foo, bar ->
            data.eachWithIndex{ char entry, int i ->
                chars[i] = entry
            }
            return chars.size()
        }] as Reader
        def mockListenInputStream = mock(InputStream.class)
        def mockListenSock = [getInputStream: {mockListenInputStream}] as Socket

        mockListenInputStream.metaClass.newReader = {
            mockListenReader
        }

        PacketPasser passer = new PacketPasser(mockListenSock)

    when: "data is sent to the passer from the client"
        passer.start(mockMinecraftServerSock)

    then: "the server socket receives the data"
        sentdata == data

    where: "we try a few different pieces of data"
        //We will always know the size of the packet from its id, so testing different length data without that
        //information would be unfair.
        data << ["Hello World!".toCharArray(), "Also Twelve!".toCharArray()]
    }
}

