package pl.sulazula.autolib.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table
@Data
public class Repair {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
