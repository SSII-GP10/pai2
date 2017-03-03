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
    private Client clientOrigin;
    private Client clientDestination;
    private double money;
    private String mac;
    private boolean integrity;
    private Date date;

    public Message(int id, Client clientOrigin, Client clientDestination, double money, String mac, boolean integrity, Date date) {
        this.id = id;
        this.clientOrigin = clientOrigin;
        this.clientDestination = clientDestination;
        this.money = money;
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

    public Client getClientOrigin() {
        return clientOrigin;
    }

    public void setClientOrigin(Client clientOrigin) {
        this.clientOrigin = clientOrigin;
    }

    public Client getClientDestination() {
        return clientDestination;
    }

    public void setClientDestination(Client clientDestination) {
        this.clientDestination = clientDestination;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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
        return "Origin: " + this.getClientOrigin().toString() + " | Destination: " + this.getClientDestination().toString()
                + " | Money: " + this.getMoney() + " | Integrity: " + integrityStr + " | " + "Date: " + formatted;
    }
}
