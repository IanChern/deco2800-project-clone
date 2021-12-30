package deco2800.spooky.networking.messages;

public class ReconnectMessage implements Message {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return(username);
    }
}
