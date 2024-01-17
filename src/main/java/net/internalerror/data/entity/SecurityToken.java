package net.internalerror.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "table_security_token")
public final class SecurityToken extends EntityBase {

  @OneToOne(optional = false)
  @JoinColumn(name = "column_user_id", nullable = false, unique = true)
  private User user;

  @Column(name = "column_valid_until", nullable = false)
  private Instant validUntil;

  @Column(name = "column_token", nullable = false, unique = true)
  private String token;

}