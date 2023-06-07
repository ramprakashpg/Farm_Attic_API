package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Introspected
@Entity
@Table(name="tbl_tam_man")
public class Man {

    @Id
    private int id;
}
