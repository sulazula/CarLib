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

    public Car save(Car car) {

        return cr.save(car);
    }

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
        if (carNumber == null) {
            return false;
        }

        if (carNumber.length() == 4) {
            if (!carNumber.matches("^[A-Za-z]{1}[0-9]{3}$")) {
                showAlert("Car number must start with one letter and have 3 digits (e.g., A123).");
                return false;
            }
        } else if (carNumber.length() == 6 || carNumber.length() == 7 || carNumber.length() == 8) {
            // valid format
            boolean valid = carNumber.matches("^[A-Za-z]{3}[0-9]{3}$") ||       // XYZ123 (until 2000y)
                    carNumber.matches("^[A-Za-z]{3}[0-9]{5}$") ||               // XYZ12345
                    carNumber.matches("^[A-Za-z]{3}[0-9]{3}[A-Za-z]{2}$") ||    // XYZ123AB
                    carNumber.matches("^[A-Za-z]{2}[0-9]{5}$");                 // XY12345

            if (!valid) {
                showAlert("Car number format is invalid.\nExample: XYZ123, XYZ12345, XYZ123AB, XY12345.");
                return false;
            }
        } else {
            showAlert("Car number must be 4, 6, 7, or 8 characters long.");
            return false;
        }

        return true;
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

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}
