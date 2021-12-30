package deco2800.spooky.networking.messages;

public class EntityDeleteMessage implements Message {
    private int entityID;

    public int getEntityID() {
        return(entityID);
    }

    public void setEntityID(int entID) {
        entityID = entID;
    }
}
