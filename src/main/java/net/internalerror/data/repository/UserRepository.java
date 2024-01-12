package net.internalerror.data.repository;

import net.internalerror.data.base.EntityBaseRepository;
import net.internalerror.data.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends EntityBaseRepository<User> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

}