package pl.sulazula.autolib.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private int year;
    private String number;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Repair> repairs;
}
