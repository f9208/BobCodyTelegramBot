package ru.bobcody.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Entity
@Table
public class City implements Serializable {
    private static final long serialVersionUID = 754535777234452L;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    Long id;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    String name;
}
