package net.internalerror.data.repository;

import lombok.Generated;
import net.internalerror.data.base.EntityBaseRepository;
import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.entity.User;
import org.springframework.stereotype.Repository;

@Generated
@Repository
public interface SecurityTokenRepository extends EntityBaseRepository<SecurityToken> {

    boolean existsByToken(String token);

    SecurityToken findByUser(User user);

    boolean existsByUser(User user);

    SecurityToken findByToken(String token);

}