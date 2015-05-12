package pl.allegro.promo.geecon2015.stub

import com.github.tomakehurst.wiremock.WireMockServer

import static com.github.tomakehurst.wiremock.client.WireMock.*

class TransactionServiceStub {

    private final WireMockServer server

    private String uuid

    private List<BigDecimal> amount

    private boolean fail

    TransactionServiceStub(WireMockServer server) {
        this.server = server
    }

    static TransactionServiceStub transactionService(WireMockServer server) {
        return new TransactionServiceStub(server);
    }

    TransactionServiceStub whenRequested(String uuid) {
        this.uuid = uuid
        return this
    }

    TransactionServiceStub fails() {
        this.fail = true
        return this
    }

    TransactionServiceStub returns(BigDecimal... amount) {
        this.amount = Arrays.asList(amount)
        return this
    }

    void teach() {
        if (fail) {
            server.stubFor(get(urlEqualTo("/transactions/$uuid")).willReturn(aResponse().withStatus(500)))
        } else {
            server.stubFor(get(urlEqualTo("/transactions/$uuid"))
                    .willReturn(aResponse().withBody("""
{
    "transactions": [ ${ amount.collect( { " { \"userId\": \"$uuid\", \"amount\": $it } " }).join(',') } ]
}
""")
                    .withHeader("Content-Type", "application/json"))
            )
        }
    }
}
