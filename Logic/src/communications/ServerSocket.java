package communications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSocket {

    private Socket socket;
    
    public ServerSocket(Socket socket){
        this.socket = socket;
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

    public void closeConnection() throws IOException {
        socket.close();
    }
}
