package domain;

/**
 * Clase que representa la entidad Cliente
 *
 * @author Matias Crizul
 */
public class Client {

    private int id;
    private String numberAccount;
    private String key;

    public Client(int id, String numberAccount, String key){
        this.id = id;
        this.numberAccount = numberAccount;
        this.key = key;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return this.getNumberAccount();
    }
}
