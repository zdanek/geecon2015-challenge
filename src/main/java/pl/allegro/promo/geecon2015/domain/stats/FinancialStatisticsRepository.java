package pl.allegro.promo.geecon2015.domain.stats;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface FinancialStatisticsRepository {

    FinancialStats listUsersWithMinimalIncome(BigDecimal minimalIncome, int limit);
    
}
