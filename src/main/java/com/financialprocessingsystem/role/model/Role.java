package com.financialprocessingsystem.role.model;

import com.financialprocessingsystem.common.AbstractEntity;
import com.financialprocessingsystem.role.enums.RoleName;
import com.financialprocessingsystem.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "ROLES")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private RoleName name;

    @OneToMany(mappedBy = "role")
    private List<User> user;

}
