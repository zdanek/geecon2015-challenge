package pl.allegro.promo.geecon2015.domain.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTransactions {
    
    private final List<UserTransaction> transactions = new ArrayList<>();

    public UserTransactions(@JsonProperty("transactions") List<UserTransaction> transactions) {
        this.transactions.addAll(transactions);
    }

    public List<UserTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
