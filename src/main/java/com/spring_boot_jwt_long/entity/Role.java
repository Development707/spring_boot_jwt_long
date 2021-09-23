package com.spring_boot_jwt_long.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "table_role")
@Getter
@Setter
public class Role extends BaseEntity {
    private String roleName;
    private String roleKey;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "table_role_perission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "premission_id")})
    private Set<Permission> permissions = new HashSet<>();
}
