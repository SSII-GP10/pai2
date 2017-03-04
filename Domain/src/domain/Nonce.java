package domain;

public class Nonce {
    private int id;
    private Client client;
    private int clientNonce;
    private int serverNonce;
   
    public Nonce(int id, Client client, int clientNonce, int serverNonce){
        this.id = id;
        this.client = client;
        this.clientNonce = clientNonce;
        this.serverNonce = serverNonce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getClientNonce() {
        return clientNonce;
    }

    public void setClientNonce(int clientNonce) {
        this.clientNonce = clientNonce;
    }

    public int getServerNonce() {
        return serverNonce;
    }

    public void setServerNonce(int serverNonce) {
        this.serverNonce = serverNonce;
    }
    
}
