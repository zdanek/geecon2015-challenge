package pl.allegro.promo.geecon2015.domain.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class FinancialStats implements Iterable<UUID> {
    
    private final List<UUID> userIds = new ArrayList<>();

    @JsonCreator
    public FinancialStats(@JsonProperty("userIds") List<UUID> userIds) {
        this.userIds.addAll(userIds);
    }

    public List<UUID> getUserIds() {
        return Collections.unmodifiableList(userIds);
    }

    @Override
    public Iterator<UUID> iterator() {
        return getUserIds().iterator();
    }
}
