package connections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;

public class ServerConnection {
    /*private ServerSocket socket;

    public ServerConnection(int port) throws IOException {
        ServerSocketFactory socketFactory = (ServerSocketFactory)ServerSocketFactory.getDefault();
        socket = (ServerSocket)socketFactory.createServerSocket(port);
    }
    
    private void runServer() {
        while (true) {
            try {
                System.err.println("Esperando conexiones de clientes...");
                BufferedReader input = new BufferedReader();
                Socket socket = (Socket) serverSocket.accept();
// abre un BufferedReader para leer los datos del cliente
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
// abre un PrintWriter para enviar datos al cliente
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
// se lee del cliente el mensaje y el macdelMensajeEnviado
                String mensaje = input.readLine();
                String macdelMensajeEnviado = input.readLine();
// a continuación habría que calcular el macdelMensajeEnviado que podría ser //macdelMensajeCalculado y tener en cuenta los nonces para evitar los ataques de replay ......................................
                if (macMensajeEnviado.equals(macdelMensajeCalculado)) {
                    output.println("Mensaje enviado integro ");
                } else {
                    output.println("Mensaje enviado no integro.");
                }
                output.close();
                input.close();
                socket.close();
            } catch (IOExceptionioException) {
                ioException.printStackTrace();
            }
        }
    }*/
}
