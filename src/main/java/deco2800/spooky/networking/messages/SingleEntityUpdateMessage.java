package deco2800.spooky.networking.messages;

import deco2800.spooky.entities.AbstractEntity;

/**
 * Updates (or creates) a single entity in the game.
 */
public class SingleEntityUpdateMessage implements Message {
    private AbstractEntity entity;

    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    public AbstractEntity getEntity() {
        return(entity);
    }
}
