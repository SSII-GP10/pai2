package connections;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.SocketFactory;

public class ClientConnection {

    private String serverIp;
    private int serverPort;

    private Socket socket;

    public ClientConnection(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.socket = null;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void openConnection() throws IOException {
        try {
            SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();
            socket = (Socket) socketFactory.createSocket(serverIp, serverPort);
        } catch (IOException ex) {
            throw new IOException("Server not aviable");
        }
    }

    public void closeConnection() throws IOException {
        try {
            socket.close();
        } catch (IOException ex) {
            throw new IOException("Error at closing connection with server");
        }
    }

    public OutputStream getOutputStream() throws IOException {
        OutputStream out;
        try {
            out = socket.getOutputStream();
        } catch (IOException ex) {
            throw new IOException("Error connection with server");
        }
        return out;
    }

    public InputStream getInputStream() throws IOException {
        InputStream in;
        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Error connection with server");
        }
        return in;
    }
}
