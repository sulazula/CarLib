package pl.sulazula.autolib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sulazula.autolib.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAll();
    Optional<Car> findByNumber(String number);
    Car findById(long id);
}
