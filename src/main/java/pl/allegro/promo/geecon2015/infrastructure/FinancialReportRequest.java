package pl.allegro.promo.geecon2015.infrastructure;

import java.math.BigDecimal;

public class FinancialReportRequest {

    private final BigDecimal minimalIncome;

    private final int limit;

    public FinancialReportRequest(BigDecimal minimalIncome, int limit) {
        this.minimalIncome = minimalIncome;
        this.limit = limit;
    }

    public BigDecimal getMinimalIncome() {
        return minimalIncome;
    }

    public int getLimit() {
        return limit;
    }
}
