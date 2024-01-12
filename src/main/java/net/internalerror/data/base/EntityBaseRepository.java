package net.internalerror.data.base;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityBaseRepository<T extends EntityBase> extends CrudRepository<T, Long> {

}