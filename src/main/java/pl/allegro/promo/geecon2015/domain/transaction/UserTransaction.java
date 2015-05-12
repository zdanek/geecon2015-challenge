package pl.allegro.promo.geecon2015.domain.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class UserTransaction {
    
    private final UUID userId;
    
    private final BigDecimal amount;

    @JsonCreator
    public UserTransaction(@JsonProperty("userId") UUID userId,
                           @JsonProperty("amount") BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
