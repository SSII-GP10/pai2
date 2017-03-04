package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa la entidad Mensaje
 *
 * @author Matias Crizul
 */
public class Message {

    private int id;
    private Client client;
    private String message;
    private String mac;
    private boolean integrity;
    private Date date;

    public Message(int id, Client client, String message, String mac, boolean integrity, Date date) {
        this.id = id;
        this.client = client;
        this.message = message;
        this.mac = mac;
        this.integrity = integrity;
        this.date = date;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isIntegrity() {
        return integrity;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        String formatted = new SimpleDateFormat("yyyy-MM-dd").format(getDate());
        String integrityStr = this.isIntegrity() ? "Yes" : "No";
        return "Client: " + this.getClient().toString() + " | Message: " + this.getMessage()
                + " | Mac: " + this.getMac() + " | Integrity: " + integrityStr + " | " + "Date: " + formatted;
    }

}
