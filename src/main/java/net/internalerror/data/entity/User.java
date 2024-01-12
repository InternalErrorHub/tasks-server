package net.internalerror.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.internalerror.data.base.EntityBase;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "table_user")
public final class User extends EntityBase {

    @Column(name = "column_firstname", nullable = false)
    private String firstname;

    @Column(name = "column_lastname", nullable = false)
    private String lastname;

    @Column(name = "column_email", nullable = false, unique = true)
    private String email;

    @Column(name = "column_password", nullable = false)
    private String password;

}