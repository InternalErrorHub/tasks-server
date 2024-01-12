package net.internalerror.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.internalerror.data.base.EntityBase;

import java.time.Instant;

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