package deco2800.spooky.networking.messages;

public class CharacterSelectedMessage implements Message {
    private String character;
    private Boolean success = true;
    private String username;

    public CharacterSelectedMessage(String character){
        this.character = character;
    }

    public CharacterSelectedMessage() {
        /* stub */
    }

    public String getCharacter() {
        return character;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
