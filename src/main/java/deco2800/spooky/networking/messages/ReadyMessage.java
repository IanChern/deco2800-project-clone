package deco2800.spooky.networking.messages;

public class ReadyMessage implements Message {
    private String username;
    private boolean isReady;

    public ReadyMessage(){

    }

    public ReadyMessage(String username, boolean isReady){
        this.username = username;
        this.isReady = isReady;
    }

    public String getUsername() {
        return(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsReady() {
        return(isReady);
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
}
