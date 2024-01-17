package net.internalerror.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.internalerror.data.base.EntityBase;

@Generated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "table_task")
public final class Task extends EntityBase {

  @Column(name = "column_name", nullable = false)
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "column_user_id", nullable = false)
  private User user;

  @Column(name = "column_details", nullable = false)
  private String details;

  @Column(name = "column_due", nullable = false)
  private Instant due;

}