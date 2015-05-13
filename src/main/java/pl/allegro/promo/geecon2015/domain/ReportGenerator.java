package pl.allegro.promo.geecon2015.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import pl.allegro.promo.geecon2015.domain.stats.FinancialStatisticsRepository;
import pl.allegro.promo.geecon2015.domain.stats.FinancialStats;
import pl.allegro.promo.geecon2015.domain.transaction.TransactionRepository;
import pl.allegro.promo.geecon2015.domain.transaction.UserTransaction;
import pl.allegro.promo.geecon2015.domain.transaction.UserTransactions;
import pl.allegro.promo.geecon2015.domain.user.User;
import pl.allegro.promo.geecon2015.domain.user.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class ReportGenerator {

    private static final UserTransactions TRANSACTION_ERROR_OBJ = new UserTransactions(Collections.EMPTY_LIST);

    private final FinancialStatisticsRepository financialStatisticsRepository;

    private final UserRepository userRepository;

    private final TransactionRepository transactionRepository;

    @Autowired
    public ReportGenerator(FinancialStatisticsRepository financialStatisticsRepository,
                           UserRepository userRepository,
                           TransactionRepository transactionRepository) {
        this.financialStatisticsRepository = financialStatisticsRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Report generate(ReportRequest request) {
        Report report = new Report();
        FinancialStats financialStats = financialStatisticsRepository.listUsersWithMinimalIncome(request.getMinimalIncome(), request.getUsersToCheck());
        for (UUID userId : financialStats.getUserIds()) {
            User user = userRepository.detailsOf(userId);
            UserTransactions usrTransactions = tryToGetUserTransactions(userId);
            BigDecimal transactionAmount = usrTransactions == TRANSACTION_ERROR_OBJ ? null : collect(usrTransactions.getTransactions());
            report.add(new ReportedUser(userId, user.getName(), transactionAmount));
        }
        return report;
    }

    private UserTransactions tryToGetUserTransactions(UUID userId) {
        try {
            return transactionRepository.transactionsOf(userId);
        } catch (HttpServerErrorException e) {
            return TRANSACTION_ERROR_OBJ;
        }

    }

    private BigDecimal collect(List<UserTransaction> transactions) {

        BigDecimal sum = new BigDecimal(0);
        for (UserTransaction userTransaction : transactions) {
            sum = sum.add(userTransaction.getAmount());
        }
        return sum;
    }

}
