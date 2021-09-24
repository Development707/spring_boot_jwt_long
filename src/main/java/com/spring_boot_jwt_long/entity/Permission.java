package com.spring_boot_jwt_long.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
public class Permission extends BaseEntity {
    private String permissionName, permissionKey;
}
