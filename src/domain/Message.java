package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa la entidad Message
 *
 * @author Matias Crizul
 */
public class Message {

    private int id;
    private String clientOrigin;
    private String clientDestination;
    private double money;
    private String mac;
    private boolean integrity;
    private Date date;

    public Message(int id, String clientOrigin, String clientDestination, double money, String mac, boolean integrity, Date date) {
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

    public String getClientOrigin() {
        return clientOrigin;
    }

    public void setClientOrigin(String clientOrigin) {
        this.clientOrigin = clientOrigin;
    }

    public String getClientDestination() {
        return clientDestination;
    }

    public void setClientDestination(String clientDestination) {
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
        String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getDate());
        String integrityStr = this.isIntegrity() ? "Yes" : "No";
        return "Origin: " + this.getClientOrigin() + " | Destination: " + this.getClientDestination() + " | Money: " + this.getMoney()
                + " | Integrity: " + integrityStr + " | " + "Date: " + formatted;
    }
}
