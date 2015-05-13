package pl.allegro.promo.geecon2015

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import org.junit.ClassRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import pl.allegro.promo.geecon2015.domain.Report
import pl.allegro.promo.geecon2015.domain.ReportGenerator
import pl.allegro.promo.geecon2015.domain.ReportRequest
import pl.allegro.promo.geecon2015.domain.ReportedUser
import pl.allegro.promo.geecon2015.report.TravisReporter
import spock.lang.Shared
import spock.lang.Specification

import static pl.allegro.promo.geecon2015.stub.FinancialStatisticsServiceStub.financialStatisticsService
import static pl.allegro.promo.geecon2015.stub.TransactionServiceStub.transactionService
import static pl.allegro.promo.geecon2015.stub.UserServiceStub.userService

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = MixerRunner.class)
@IntegrationTest(["server.port=0",
        "financial.uri=http://localhost:45367",
        "user.uri=http://localhost:45368",
        "transaction.uri=http://localhost:45369"
])
class GeeconChallengeTest extends Specification {

    public static final String UUID_A = "ee481f6c-dcce-41d1-89b8-bbc268dec843"
    public static final String UUID_B = "fb0d5e34-6317-4add-a8d9-6e4ae415f31e"
    @ClassRule
    @Shared
    WireMockClassRule financialStatsServer = new WireMockClassRule(45367)

    @ClassRule
    @Shared
    WireMockClassRule userServer = new WireMockClassRule(45368)

    @ClassRule
    @Shared
    WireMockClassRule transactionServer = new WireMockClassRule(45369)

    @Autowired
    ReportGenerator reportGenerator

    void setup() {
        financialStatisticsService(financialStatsServer)
                .forMinimalIncome(1000)
                .forLimit(2)
                .willReturn(UUID_A, UUID_B)
                .teach()
    }

    void cleanupSpec() {
        new TravisReporter().report()
    }

    def "should generate report for all users"() {
        given:
        userService(userServer).whenRequested(UUID_A).returns("User A").teach()
        userService(userServer).whenRequested(UUID_B).returns("User B").teach()

        transactionService(transactionServer).whenRequested(UUID_A).returns(10, 30).teach()
        transactionService(transactionServer).whenRequested(UUID_B).returns(20, 50).teach()

        when:
        Report report = reportGenerator.generate(new ReportRequest(1000, 2))

        then:
        report.size() == 2
        report.toSet() == [
                new ReportedUser(UUID.fromString(UUID_A), "User A", 40),
                new ReportedUser(UUID.fromString(UUID_B), "User B", 70)
        ] as Set
    }

    def "should attach <failed> string instead of name in report, when failed to fetch user data"() {
        given:
        userService(userServer).whenRequested(UUID_A).returns("User A").teach()
        userService(userServer).whenRequested(UUID_B).fails().teach()

        transactionService(transactionServer).whenRequested(UUID_A).returns(10).teach()
        transactionService(transactionServer).whenRequested(UUID_B).returns(20).teach()

        when:
        Report report = reportGenerator.generate(new ReportRequest(1000, 2))

        then:
        report.size() == 2
        report.toSet() == [
                new ReportedUser(UUID.fromString(UUID_A), "User A", 10),
                new ReportedUser(UUID.fromString(UUID_B), "<failed>", 20)
        ] as Set
    }

    def "should return null as transaction amount in report when failed to fetch transactions data"() {
        given:
        userService(userServer).whenRequested(UUID_A).returns("User A").teach()
        userService(userServer).whenRequested(UUID_B).returns("User B").teach()

        transactionService(transactionServer).whenRequested(UUID_A).returns(10).teach()
        transactionService(transactionServer).whenRequested(UUID_B).fails().teach()

        when:
        Report report = reportGenerator.generate(new ReportRequest(1000, 2))

        then:
        report.size() == 2
        report.toSet() == [
                new ReportedUser(UUID.fromString(UUID_A), "User A", 10),
                new ReportedUser(UUID.fromString(UUID_B), "User B", null)
        ] as Set
    }
}
