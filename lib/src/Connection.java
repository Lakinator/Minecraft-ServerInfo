import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * 13.05.2017
 * Created by user Schalk (Lukas Schalk).
 */

public class Connection {
    private URL url;
    private Scanner scanner;
    private String address;
    private String port;
    private String result;

    public Connection(String address, String port) {
        this.address = address;
        this.port = port.isEmpty() ? "25565" : port;
    }

    public void open() {
        try {
            url = new URL("https://mcapi.us/server/status?ip=" + address + "&port=" + port);
            scanner = new Scanner(url.openStream(), "UTF-8");

            result = scanner.useDelimiter("\\A").next();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        scanner.close();
    }

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }

    public String getResult() {
        return result;
    }
}
