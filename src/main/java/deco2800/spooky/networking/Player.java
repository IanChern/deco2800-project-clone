package deco2800.spooky.networking;

public class Player {

    /**
     * Player object to hold player's connection information
     */
    private String clientIP;

    private String username;

    private String character;

    private boolean isReady = false;

    private int id;

    public Player(String clientIP, String username){
        this.clientIP = clientIP;
        this.username = username;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getUsername() {
        return username;
    }

    public boolean isReadyGame(){
        return isReady;
    }

    public void getReady(boolean isReady){
        this.isReady = isReady;
    }

    public void changeId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
