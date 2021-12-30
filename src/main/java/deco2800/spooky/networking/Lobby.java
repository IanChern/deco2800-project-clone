package deco2800.spooky.networking;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby {
    /**
     * IP address to connect to the host-game client
     * */
    private InetAddress hostIp;

    /**
     * Currently for testing purpose only
     * */
    private final int lobbyID;

    /**
     * Client username
     * */
    private String username;
    private List<Player> players = new ArrayList<>();
    private HashMap<String, Player> playerDict = new HashMap<>();
    public static final int NUMPLAYER = 1;
    private ArrayList<Player> readyList = new ArrayList<>();

    public Lobby (InetAddress hostIp, String username, int lobbyID){
        this.hostIp = hostIp;
        this.username = username;
        this.lobbyID = lobbyID;
    }


    public InetAddress getHostIp() {
        return hostIp;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public String getUsername() {
        return username;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<String, Player> getPlayersByName() {
        return playerDict;
    }

    public int numOfPlayers(){
        return players.size();
    }

    public void addPlayer(String username, String clientIP){
        players.add(new Player(clientIP, username));
        playerDict.put(username, players.get(players.size() - 1));
    }

    public int getMaxPlayers() {
        return NUMPLAYER;
    }

    public void addReadyPlayer(Player p){
        this.readyList.add(p);
    }
    public List<Player> getReadyPlayers(){
        return this.readyList;
    }
}
