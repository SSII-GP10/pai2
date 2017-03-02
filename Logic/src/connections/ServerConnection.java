package connections;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;

public class ServerConnection {

    private int port;

    private ServerSocket serverSocket;
    private Socket socket;

    public ServerConnection(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void openServer() throws IOException {
        try {
            ServerSocketFactory socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();
            serverSocket = (ServerSocket) socketFactory.createServerSocket(port);
        } catch (IOException ex) {
            throw new IOException("Network problems");
        }
    }

    public void openConnections() throws IOException {
        socket = (Socket) serverSocket.accept();
    }

    public OutputStream getOutputStream() throws IOException {
        OutputStream out;
        try {
            out = socket.getOutputStream();
        } catch (IOException ex) {
            throw new IOException("Error connection with client");
        }
        return out;
    }

    public InputStream getInputStream() throws IOException {
        InputStream in;
        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            throw new IOException("Error connection with client");
        }
        return in;
    }

    public void closeConnections() throws IOException {
        socket.close();
    }
}
