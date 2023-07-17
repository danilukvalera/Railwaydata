package com.danyliuk.testjob.railwaydata.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 32)
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    private ArrayList<Carriage> listCarriages;

}
