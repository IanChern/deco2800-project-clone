package deco2800.spooky.networking.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChatMessageTest {

    @Test
    public void testToString() {
        ChatMessage cm = new ChatMessage();
        ChatMessage cm2 = new ChatMessage("Username", "Message");
        assertEquals("[Username] Message", cm2.toString());
    }
}