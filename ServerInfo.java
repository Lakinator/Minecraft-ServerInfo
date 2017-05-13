package MCServerInfo;

/**
 * 13.05.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class ServerInfo {
    private Connection connection;
    private boolean exists;
    private boolean status;
    private int maxPlayers, onlinePlayers;
    private String version;
    private String description;


    public ServerInfo(String address, String port) {
        connection = new Connection(address, port);
        connection.open();

        Decoder decoder = new Decoder(connection.getResult());

        if (decoder.get("error", false, null) == "missing data") {
            System.err.println("IP Addresse fehlt");
            exists = false;
        } else if (decoder.get("error", false, null) == "invalid hostname or port") {
            System.err.println("Addresse oder Port falsch angegeben");
            exists = false;
        } else if (decoder.get("error", false, null) == "internal server error") {
            System.err.println("Server nicht erreichbar");
            exists = false;
        } else if (decoder.get("last_online", false, null).isEmpty()) {
            System.err.println("Server existiert nicht");
            exists = false;
        } else {
            status = Boolean.getBoolean(decoder.get("online", false, null));
            maxPlayers = Integer.parseInt(decoder.get("players", true, "max"));
            onlinePlayers = Integer.parseInt(decoder.get("players", true, "now"));
            version = decoder.get("server", true, "name");
            description = decoder.get("motd", false, null).replaceAll("§.", "");
            exists = true;
        }


        connection.close();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public boolean getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public boolean exists() {
        return exists;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return connection.getAddress() + ":" + connection.getPort();
    }
}
