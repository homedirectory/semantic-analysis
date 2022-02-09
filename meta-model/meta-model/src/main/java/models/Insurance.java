package main.java.models;

import java.math.BigDecimal;
import java.util.Date;

public class Insurance {
    private BigDecimal cost;
    private Date expirationDate;
    
    public Insurance(BigDecimal cost, Date expirationDate) {
        this.cost = cost;
        this.expirationDate = expirationDate;
    }

    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
