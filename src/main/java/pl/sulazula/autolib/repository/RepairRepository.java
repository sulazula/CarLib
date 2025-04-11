package pl.sulazula.autolib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sulazula.autolib.model.Repair;

import java.util.List;

public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByCarId(Long carId);
}
