package deco2800.spooky.networking.messages;

public class DisconnectMessage implements Message {
    private String username;
    private boolean disconnect;

    public String getUsername() {
        return(username);
    }

    public boolean getDisconnect() {
        return(disconnect);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisconnect(boolean isDisconnect) {
        disconnect = isDisconnect;
    }
}
