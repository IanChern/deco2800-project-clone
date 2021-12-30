package deco2800.spooky.networking.messages;

import deco2800.spooky.entities.AbstractEntity;

public class ConnectMessage implements Message {
    private String username;
    private AbstractEntity playerEntity;

    public String getUsername() {
        return(username);
    }

    public AbstractEntity getPlayerEntity() {
        return(playerEntity);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlayerEntity(AbstractEntity ae) {
        this.playerEntity = ae;
    }
}
