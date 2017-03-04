package communications;

import helpers.ReaderPlus;
import helpers.WriterPlus;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerCommunication {

    private ServerSocket connection;
    private ReaderPlus reader;
    private WriterPlus writer;
    
    public ServerCommunication(Socket socket) throws IOException {
        this.connection = new ServerSocket(socket);
        prepareCommunication();
    }

    public String[] readMessage() throws IOException {
        String[] ret = new String[2];
        ret[0] = reader.nextLine();
        ret[1] = reader.nextLine();
        return ret;
    }

    public void sendMessage(String message, String mac) throws IOException {
        writer.writeLine(message);
        writer.writeLine(mac);
        writer.flush();
    }

    public void closeConnection() throws IOException {
        reader.close();
        writer.close();
        connection.closeConnection();
    }

    private void prepareCommunication() throws IOException {
        InputStream inStream = connection.getInputStream();
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        reader = new ReaderPlus(inStreamReader, 3000);
        OutputStream outStream = connection.getOutputStream();
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream);
        writer = new WriterPlus(outStreamWriter, 3000);
    }
}
