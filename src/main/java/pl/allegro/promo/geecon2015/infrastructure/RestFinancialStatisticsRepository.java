package pl.allegro.promo.geecon2015.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.allegro.promo.geecon2015.domain.stats.FinancialStatisticsRepository;
import pl.allegro.promo.geecon2015.domain.stats.FinancialStats;

import java.math.BigDecimal;

@Component
public class RestFinancialStatisticsRepository implements FinancialStatisticsRepository {

    private final String baseUri;

    private final RestTemplate restTemplate;
    
    @Autowired
    public RestFinancialStatisticsRepository(@Value("${financial.uri}") String baseUri, RestTemplate restTemplate) {
        this.baseUri = baseUri;
        this.restTemplate = restTemplate;
    }

    @Override
    public FinancialStats listUsersWithMinimalIncome(BigDecimal minimalIncome, int limit) {
        return restTemplate.postForEntity(baseUri + "/statistics", new FinancialReportRequest(minimalIncome, limit), FinancialStats.class).getBody();
    }
}
