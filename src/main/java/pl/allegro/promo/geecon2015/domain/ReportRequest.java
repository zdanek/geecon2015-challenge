package pl.allegro.promo.geecon2015.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class ReportRequest {
    
    private final BigDecimal minimalIncome;
    
    private final int usersToCheck;

    @JsonCreator
    public ReportRequest(BigDecimal minimalIncome, int usersToCheck) {
        this.minimalIncome = minimalIncome;
        this.usersToCheck = usersToCheck;
    }

    public BigDecimal getMinimalIncome() {
        return minimalIncome;
    }

    public int getUsersToCheck() {
        return usersToCheck;
    }
}
