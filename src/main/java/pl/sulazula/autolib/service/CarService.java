package pl.sulazula.autolib.service;

import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import pl.sulazula.autolib.model.Car;
import pl.sulazula.autolib.model.Repair;
import pl.sulazula.autolib.repository.CarRepository;
import pl.sulazula.autolib.repository.RepairRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository cr;
    @Autowired
    private RepairRepository rr;

    public List<Car> findAll() {

        return cr.findAll();
    }

    public Car findById(Long id) {
        return cr.findById(id).orElse(null);
    }

    public boolean checkIfCarExist(String number) {
        return cr.findByNumber(number).isPresent();
    }

    public boolean checkIllegal(String carNumber) {

        if (carNumber.length() == 4) {

            if (!carNumber.matches("^[A-Za-z]{1}.{3}$")) {

                Alert alert = new Alert(Alert.AlertType.ERROR, "Car number must start with one letter and have a total of 4 characters.");
                alert.showAndWait();
                return false;
            }
        } else if (carNumber.length() == 8) {

            if (!carNumber.matches("^[A-Za-z]{3}.{5}$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Car number must start with three letters and have a total of 8 characters.");
                alert.showAndWait();
                return false;
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Car number must be either 4 or 8 characters long.");
            alert.showAndWait();
            return false;
        }

        return true;
    }


    public Car save(Car car) {

        return cr.save(car);
    }

    public Repair setNewRepair(String number, String description, LocalDate repairDate) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Car number cannot be empty");
        }

        return cr.findByNumber(number.trim().toUpperCase())
                .map(car -> {
                    Repair repair = new Repair();
                    repair.setDate(repairDate);
                    repair.setDescription(description);
                    repair.setCar(car);
                    return rr.save(repair);
                })
                .orElseThrow(() -> new IllegalArgumentException("Car with number " + number + " not found"));
    }
}
