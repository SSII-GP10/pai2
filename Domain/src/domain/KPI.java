package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa la entidad KPI
 *
 * @author Matias Crizul
 */
public class KPI {

    private int id;
    private int positives;
    private int total;
    private Date reportDate;

    public KPI(int id, int positives, int total, Date reportDate) {
        this.id = id;
        this.positives = positives;
        this.total = total;
        this.reportDate = reportDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRatio() {
        return this.positives / (this.total * 1.);
    }

    public int getPositives() {
        return positives;
    }

    public void setPositives(int positives) {
        this.positives = positives;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String toString() {
        String formatted = new SimpleDateFormat("yyyy-MM-dd").format(getReportDate());
        return "Ratio: " + this.getRatio() + " | Positives: " + getPositives() + " | Total: " + getTotal()
                + " | Report: " + formatted;
    }

}
