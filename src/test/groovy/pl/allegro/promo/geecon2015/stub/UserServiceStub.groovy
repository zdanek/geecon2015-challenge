package pl.allegro.promo.geecon2015.stub

import com.github.tomakehurst.wiremock.WireMockServer

import static com.github.tomakehurst.wiremock.client.WireMock.*

class UserServiceStub {

    private final WireMockServer server

    private String uuid

    private String name

    private int limit

    private boolean fail

    UserServiceStub(WireMockServer server) {
        this.server = server
    }

    static UserServiceStub userService(WireMockServer server) {
        return new UserServiceStub(server)
    }

    UserServiceStub whenRequested(String uuid) {
        this.uuid = uuid
        return this
    }

    UserServiceStub fails() {
        this.fail = true
        return this
    }

    UserServiceStub returns(String name) {
        this.name = name
        return this
    }

    void teach() {
        if (fail) {
            server.stubFor(get(urlEqualTo("/users/$uuid")).willReturn(aResponse().withStatus(500)))
        } else {
            server.stubFor(get(urlEqualTo("/users/$uuid"))
                    .willReturn(aResponse().withBody("""
    { "id": "$uuid", "name": "$name" }
""")
                    .withHeader("Content-Type", "application/json"))
            )
        }
    }
}
