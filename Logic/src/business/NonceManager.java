package business;

import domain.Client;
import domain.Message;
import domain.Nonce;
import java.sql.SQLException;
import java.util.Collection;
import persistence.NonceRepository;

public class NonceManager {

    public static NonceManager instance;

    public static NonceManager getInstance() {
        if (instance == null) {
            instance = new NonceManager();
        }
        return instance;
    }

    private NonceManager() {

    }

    public void addNonce(Nonce nonce) throws SQLException {
        NonceRepository.insertNonce(nonce);
    }

    public Nonce getNonce(Client client) throws SQLException {
        return NonceRepository.getNonce(client);
    }

    public void deleteNonce(Nonce nonce) throws SQLException {
        NonceRepository.deleteNonce(nonce);
    }
}
