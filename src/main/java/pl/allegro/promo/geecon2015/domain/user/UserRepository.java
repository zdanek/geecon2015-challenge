package pl.allegro.promo.geecon2015.domain.user;

import java.util.UUID;

public interface UserRepository {
    
    User detailsOf(UUID userId);
    
}
