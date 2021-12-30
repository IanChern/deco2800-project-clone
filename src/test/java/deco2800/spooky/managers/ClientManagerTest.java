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
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
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
public class ClientManagerTest {
    /**
     * Provides basic functionality of a server for testing.
     */
    private class TestServer {
        public Server server;

        public static final int TCP_PORT = 54556;
        public static final int UDP_PORT = 54778;

        public int messagesReceived = 0;

        // list of received messages
        public ArrayList<Object> messages;
    }

    private ClientManager clientMan;
    private TestServer serverMan;

    // spy's for mocking
    private Client clientSpy;
    private Server serverSpy;

    // this is a dummy so that a Client can be spy'd on
    private Client clientDummy = new Client(16384, 16384);

    private Object o;

    @Before
    public void setUp() {
        // start clientMan and server
        serverMan = new TestServer();
        clientMan = new ClientManager();
        
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
        clientMan.stop();
        clientMan.close();
        clientMan = null;
        clientSpy = null;
    }

    private void clientSetup() {
        // mock client, setting the clientManagers client to be our spy
        clientSpy = PowerMockito.spy(clientDummy);
        try {
            Field f = clientMan.getClass().getDeclaredField("client");
            FieldSetter setter = new FieldSetter(clientMan, f);
            setter.set(clientSpy);
        } catch (NoSuchFieldException nsfe) {
            // will not happen
        }

        // when a client sends a message, add to queue of server
        doAnswer(InvocationOnMock -> {
            Object[] o = InvocationOnMock.getArguments();
            serverMan.messagesReceived++;
            serverMan.messages.add(o[0]);
            return null;
        }).when(clientSpy).sendTCP(any(Message.class));
    }

    private void serverSetup() {
        // mock server
        serverMan.server = new Server(16384, 16384);
        serverSpy = spy(serverMan.server);
        serverMan.messages = new ArrayList<>();

        // when server sends message, call listener for client
        doAnswer(InvocationOnMock -> {
            Object[] o = InvocationOnMock.getArguments();
            Whitebox.invokeMethod(clientSpy, "notifyReceived", o[0]);
            return null;
        }).when(serverSpy).sendToAllTCP(any(Message.class));

        // Register the classes to be serialised with kryo
        Kryo kryo = serverSpy.getKryo();
        kryo.setReferences(true);
        // KYRO auto registration
        kryo.setRegistrationRequired(false);
        ((Kryo.DefaultInstantiatorStrategy)
                kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());

        // Start the server on ports 54555 and 54777
        serverSpy.start();
        try {
            serverSpy.bind(TestServer.TCP_PORT, TestServer.UDP_PORT);
        } catch (IOException e) {
            // squish
        }
    }

    /**
     * Tests starts and connects properly.
     */
    @Test
    public void setupTest() throws InterruptedException {
        // correct type of message was sent
        clientMan.connectToHost("localhost", "duck1234");
        assertTrue("Must send connect message", serverMan.messages.get(0) instanceof ConnectMessage);

        // check contents of message
        ConnectMessage m = (ConnectMessage)serverMan.messages.get(0);
        assertEquals("duck1234", m.getUsername());

        // check client setup properly
        assertEquals("duck1234", clientMan.localUsername);
        assertEquals(2, serverMan.messages.size());
    }

    /**
     * Tests that the clientMan reacts properly to a TileUpdateMessage.
     */
    @Test
    public void TileUpdateMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        ServerWorld mockedWorld = mock(ServerWorld.class);
        PowerMockito.mockStatic(GameManager.class);
        ArgumentCaptor<Tile> captor = ArgumentCaptor.forClass(Tile.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getWorld()).thenReturn(mockedWorld);

        // send message
        Tile t = new Tile("");
        t.setTileID(-1);
        TileUpdateMessage m = new TileUpdateMessage();
        m.setTile(t);
        serverSpy.sendToAllTCP(m);

        // verify correct tile was updated
        //Thread.sleep(50);
        verify(mockedWorld).updateTile(captor.capture());
        assertEquals(-1, captor.getValue().getTileID());
        verify(mockedWorld, times(1)).generateNeighbours();
    }

    /**
     * Tests that the clientMan reacts properly to a ChatMessage.
     */
    @Test
    public void ChatMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        OnScreenMessageManager mockedChatter = mock(OnScreenMessageManager.class);
        PowerMockito.mockStatic(GameManager.class);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getManager(OnScreenMessageManager.class)).
                thenReturn(mockedChatter);
        
        // send message
        ChatMessage m = new ChatMessage("host", "This is a test!");
        serverSpy.sendToAllTCP(m);

        // verify message was added onScreen
        //Thread.sleep(50);
        verify(mockedChatter).addMessage(captor.capture());
        assertEquals("[host] This is a test!", captor.getValue());
    }

    /**
     * Tests that the clientMan reacts properly to a SingleEntityUpdateMessage.
     */
    @Test
    public void SingleEntityUpdateMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        ServerWorld mockedWorld = mock(ServerWorld.class);
        PowerMockito.mockStatic(GameManager.class);
        ArgumentCaptor<AbstractEntity> captor = ArgumentCaptor.forClass(AbstractEntity.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getWorld()).thenReturn(mockedWorld);

        // send message
        AbstractEntity e = new StaticEntity();
        SingleEntityUpdateMessage m = new SingleEntityUpdateMessage();
        m.setEntity(e);
        serverSpy.sendToAllTCP(m);

        // verify correct entity was updated
        //Thread.sleep(50);
        verify(mockedWorld).updateEntity(captor.capture());
        assertEquals(e, captor.getValue());
    }

    /**
     * Tests that the clientMan reacts properly to a TileDeleteMessage.
     */
    @Test
    public void TileDeleteMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        ServerWorld mockedWorld = mock(ServerWorld.class);
        PowerMockito.mockStatic(GameManager.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getWorld()).thenReturn(mockedWorld);

        // send message
        TileDeleteMessage m = new TileDeleteMessage();
        m.setTileID(-1);
        serverSpy.sendToAllTCP(m);

        // verify correct tile was deleted
        //Thread.sleep(50);
        verify(mockedWorld).deleteTile(captor.capture());
        assertEquals(-1, captor.getValue().intValue());
    }

    /**
     * Tests that the clientMan reacts properly to a EntityDeleteMessage.
     */
    @Test
    public void EntityDeleteMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        ServerWorld mockedWorld = mock(ServerWorld.class);
        PowerMockito.mockStatic(GameManager.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getWorld()).thenReturn(mockedWorld);

        // send message
        EntityDeleteMessage m = new EntityDeleteMessage();
        m.setEntityID(-1);
        serverSpy.sendToAllTCP(m);

        // verify correct entity was updated
        verify(mockedWorld).deleteEntity(captor.capture());
        assertEquals(-1, captor.getValue().intValue());
    }

    /**
     * Tests that the clientMan reacts properly to a EndgameMessage.
     */
    @Test
    public void EndgameMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        PopUpManager mockedPopups = mock(PopUpManager.class);
        PowerMockito.mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getManager(PopUpManager.class)).thenReturn(mockedPopups);

        // send message
        EndgameMessage m = new EndgameMessage();
        serverSpy.sendToAllTCP(m);

        // verify prompt was tested and disconnect message sent
        verify(mockedPopups).endGamePrompt();
        int i = serverMan.messagesReceived;
        assertTrue("Client should send a DisconnectMessage", 
                serverMan.messages.get(i - 1) instanceof DisconnectMessage);
        assertEquals("duck1234", ((DisconnectMessage)(serverMan.messages.get(i - 1))).getUsername());
    }

    /**
     * Tests that the clientMan reacts properly to a CharacterSelectedMessage.
     */
    @Ignore
    public void CharacterSelectedMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        // mock objects
        GameManager mockedManager = mock(GameManager.class);
        PopUpManager mockedPopups = mock(PopUpManager.class);
        PowerMockito.mockStatic(PopUpManager.class);
        PowerMockito.mockStatic(GameManager.class);

        when(GameManager.get()).thenReturn(mockedManager);
        when(mockedManager.getManager(PopUpManager.class)).thenReturn(mockedPopups);

        // send message with success = true
        CharacterSelectedMessage m = new CharacterSelectedMessage();
        m.setSuccess(true);
        serverSpy.sendToAllTCP(m);

        // verify nothing happened
        verify(mockedPopups, never()).displayPopUpMessage(
                anyString(), anyString(), anyInt());

        // send message with success = flase
        m.setSuccess(false);
        serverSpy.sendToAllTCP(m);

        // verify popup popped up
        verify(mockedPopups).displayPopUpMessage(
                anyString(), anyString(), anyInt());
    }

    /**
     * Tests that the clientMan reacts properly to a ReadyMessage.
     */
    @Test
    public void ReadyMessageTest() throws InterruptedException {
        // start conneciton (assumes this works)
        clientMan.connectToHost("localhost", "duck1234");

        ReadyMessage m = new ReadyMessage();

        // incorrect username
        m.setUsername("client");
        m.setReady(true);
        serverSpy.sendToAllTCP(m);
        assertFalse("Username must be \"host\"", clientMan.gameReady());

        // incorrect ready status
        m.setUsername("host");
        m.setReady(false);
        serverSpy.sendToAllTCP(m);
        assertFalse("Status must be true", clientMan.gameReady());

        // correct combination
        m.setUsername("host");
        m.setReady(true);
        serverSpy.sendToAllTCP(m);
        assertTrue(clientMan.gameReady());
    }
}
