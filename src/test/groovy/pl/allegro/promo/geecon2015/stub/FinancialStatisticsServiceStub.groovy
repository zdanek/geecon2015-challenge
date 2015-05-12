package pl.allegro.promo.geecon2015.stub

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo

class FinancialStatisticsServiceStub {
    
    private final WireMockServer server
    
    private List<String> uuids
    
    private BigDecimal minimalIncome
    
    private int limit

    FinancialStatisticsServiceStub(WireMockServer server) {
        this.server = server
    }
    
    static FinancialStatisticsServiceStub financialStatisticsService(WireMockServer server) {
        return new FinancialStatisticsServiceStub(server)
    }
    
    FinancialStatisticsServiceStub forMinimalIncome(BigDecimal income) {
        this.minimalIncome = income
        return this
    }
 
    FinancialStatisticsServiceStub forLimit(int limit) {
        this.limit = limit
        return this
    }
    
    FinancialStatisticsServiceStub willReturn(String... uuids) {
        this.uuids = Arrays.asList(uuids)
        return this
    }
    
    void teach() {
        server.stubFor(post(urlEqualTo("/statistics"))
                .withRequestBody(WireMock.equalToJson("""
{ "minimalIncome": $minimalIncome, "limit": $limit }
"""))
                .willReturn(aResponse().withBody("""
   { "userIds": [ ${uuids.collect({ "\"$it\"" }).join(',')} ] }
""")
        .withHeader("Content-Type", "application/json"))
        )
    }
}
