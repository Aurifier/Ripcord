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

        OutputStream.metaClass.newWriter = {
            mockMinecraftServerWriter
        }

        def mockListenReader = [read: {chars, foo, bar ->
            chars = data
            return chars.length()
        }] as Reader
        def mockListenInputStream = mock(InputStream.class)
        def mockListenSock = [getInputStream: {mockListenInputStream}] as Socket

        InputStream.metaClass.newReader = {
            mockListenReader
        }

        PacketPasser passer = new PacketPasser(mockListenSock)

    when: "data is sent to the passer from the client"
        passer.start(mockMinecraftServerSock)

    then: "the server socket receives the data"
        sentdata == data

    cleanup:
        GroovySystem.metaClassRegistry.removeMetaClass(OutputStream.class)
        GroovySystem.metaClassRegistry.removeMetaClass(InputStream.class)

    where: "we try a few different pieces of data"
        data << ["Hello World!"]
    }
}

