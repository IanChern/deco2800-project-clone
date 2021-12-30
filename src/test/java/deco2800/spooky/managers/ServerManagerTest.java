package deco2800.spooky.managers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import org.objenesis.strategy.StdInstantiatorStrategy;

import deco2800.spooky.networking.messages.*;
import deco2800.spooky.worlds.Tile;
import deco2800.spooky.worlds.ServerWorld;
import deco2800.spooky.entities.*;
import deco2800.spooky.managers.ClientManager;
import deco2800.spooky.managers.GameManager;
import deco2800.spooky.managers.OnScreenMessageManager;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Tests the ClientManager class
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class ServerManagerTest {
    /**
     * Provides basic functionality of a server for testing.
     */
    private class TestClient {
        public Client client;

        public static final int TCP_PORT = 54556;
        public static final int UDP_PORT = 54778;

        // list of received messages
        public ArrayList<Object> messages;
    }

    private ServerManager serverMan;
    private TestClient clientMan;

    // spy's for mocking
    private Client clientSpy;
    private Server serverSpy;

    // this is a dummy so that a Server can be spy'd on
    private Server serverDummy = new Server(16384, 16384);

    private Object o;

    @Before
    public void setUp() {
        // start clientMan and server
        serverMan = new ServerManager();
        clientMan = new TestClient();
        
        serverSetup();
        clientSetup();
    }

    @After
    public void tearDown() {
        // end clientMan and server
        serverSpy.stop();
        serverSpy.close();
        serverMan = null;
        serverSpy = null;
        clientSpy.stop();
        clientSpy.close();
        clientMan = null;
        clientSpy = null;
    }

    private void clientSetup() {
        // mock client
		clientMan.client = new Client(16384, 16384);
		clientSpy = spy(clientMan.client);
		clientMan.messages = new ArrayList<>();

        // Register the classes to be serialised with kryo
        Kryo kryo = clientSpy.getKryo();
        kryo.setReferences(true);
        // KYRO auto registration
        kryo.setRegistrationRequired(false);
        ((Kryo.DefaultInstantiatorStrategy)
                kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        // when a client sends a message, add to queue of server
        doAnswer(InvocationOnMock -> {
            Object[] o = InvocationOnMock.getArguments();
            // TODO this method does not exist: need to find method in kyronet
            // that corresponds to triggering a listener
		    Whitebox.invokeMethod(serverSpy, "notifyReceived", o[0]);
            return null;
        }).when(clientSpy).sendTCP(any(Message.class));

        clientSpy.start();
    }

    private void serverSetup() {
        // mock server, placing our spy as the server managers server
        serverSpy = PowerMockito.spy(serverDummy);
        try {
            Field f = serverMan.getClass().getDeclaredField("server");
            FieldSetter setter = new FieldSetter(serverMan, f);
            setter.set(serverSpy);
        } catch (NoSuchFieldException nsfe) { // almost nsfw
            // will not happen
        }

        // when server sends message, call listener for client
        doAnswer(InvocationOnMock -> {
            Object[] o = InvocationOnMock.getArguments();
		    clientMan.messages.add(o[0]);
            return null;
        }).when(serverSpy).sendToAllTCP(any(Message.class));
    }

    /**
     * Tests ConnectMessage.
     */
    @Test
    @Ignore
    public void ConnectMessageTest() {
        try {
            clientSpy.connect(5000, "localhost", TestClient.TCP_PORT, TestClient.UDP_PORT);
        } catch (IOException ioe) {
            // squish: test will fail later
        }
        
        // send message
        ConnectMessage m = new ConnectMessage();
        m.setUsername("duck1");
        m.setPlayerEntity(null);
        clientSpy.sendTCP(m);

        // check client added 
        assertEquals(1, serverMan.numPlayers());

        // check users cannot have same username
        clientSpy.sendTCP(m);
        assertEquals(1, serverMan.numPlayers());

        // ensure max number of users is not exceeded
        for (int i = 0; i <= serverMan.maxPlayers(); i++) {
            m.setUsername("" + i);
            clientSpy.sendTCP(m);
        }
        assertEquals(serverMan.maxPlayers(), serverMan.numPlayers());
    }

    /**
     * Tests that the clientMan reacts properly to a TileUpdateMessage.
     */
    @Test
    @Ignore
    public void TileUpdateMessageTest() throws InterruptedException {
        /* TODO */
    }

    /**
     * Tests that the clientMan reacts properly to a ChatMessage.
     */
    //@Test
    @Ignore
    public void ChatMessageTest() throws InterruptedException {
    /* TODO */
    }

    /**
     * Tests that the clientMan reacts properly to a SingleEntityUpdateMessage.
     */
    //@Test
    @Ignore
    public void SingleEntityUpdateMessageTest() throws InterruptedException {
    /* TODO */
    }

    /**
     * Tests that the clientMan reacts properly to a TileDeleteMessage.
     */
    //@Test
    @Ignore
    public void TileDeleteMessageTest() throws InterruptedException {
    /* TODO */
    }

    /**
     * Tests that the clientMan reacts properly to a EntityDeleteMessage.
     */
    //@Test
    @Ignore
    public void EntityDeleteMessageTest() throws InterruptedException {
    /* TODO */
    }

    /**
     * Tests that the clientMan reacts properly to a ReadyMessage.
     */
    //@Test
    @Ignore
    public void ReadyMessageTest() throws InterruptedException {
    /* TODO */
    }
}
