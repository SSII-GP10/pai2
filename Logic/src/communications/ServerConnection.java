package communications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;

public class ServerConnection {

    private int port;

    private ServerSocket serverSocket;
    
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

    public Socket openConnection() throws IOException {
        Socket socket = (Socket) serverSocket.accept();
        return socket;
    }
    
}
