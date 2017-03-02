package business;

import domain.Client;
import java.sql.SQLException;
import java.util.Collection;
import persistence.ClientRepository;

public class ClientManager {

    public static ClientManager instance;

    public static ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void addClient(Client client) throws SQLException {
        ClientRepository.insertClient(client);
    }

    public Collection<Client> getAllClients() throws SQLException {
        return ClientRepository.getAllClients();
    }
    
    public Client getClient(String numberAccount) throws SQLException{
        return ClientRepository.getClient(numberAccount);
    }
}
