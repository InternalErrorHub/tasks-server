package net.internalerror.data.base;

import lombok.Generated;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@Generated
@NoRepositoryBean
public interface EntityBaseRepository<T extends EntityBase> extends CrudRepository<T, Long> {

}