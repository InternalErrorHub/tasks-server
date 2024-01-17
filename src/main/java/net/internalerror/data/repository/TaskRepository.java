package net.internalerror.data.repository;

import java.util.List;
import lombok.Generated;
import net.internalerror.data.base.EntityBaseRepository;
import net.internalerror.data.entity.Task;
import net.internalerror.data.entity.User;
import org.springframework.stereotype.Repository;

@Generated
@Repository
public interface TaskRepository extends EntityBaseRepository<Task> {

  boolean existsByUserAndNameIgnoreCase(User user, String name);

  Task findByUserAndNameIgnoreCase(User user, String name);

  List<Task> findByUser(User user);

}