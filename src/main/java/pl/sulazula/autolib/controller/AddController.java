package pl.sulazula.autolib.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.sulazula.autolib.impl.SpringFXMLLoader;
import pl.sulazula.autolib.model.Car;
import pl.sulazula.autolib.service.CarService;

import java.time.Year;

@Component
public class AddController {

    @FXML private Button addCarButton;
    @FXML private TextField carModelField;
    @FXML private TextField carYearField;
    @FXML private TextField carNumberField;

    private final CarService carService;

    private final SpringFXMLLoader springFXMLLoader;

    @Autowired
    public AddController(CarService carService, SpringFXMLLoader springFXMLLoader) {
        this.carService = carService;
        this.springFXMLLoader = springFXMLLoader;
    }

    @FXML
    public void addCar() {
        String carModel = carModelField.getText();

        Integer carYear = null;
        try {
            carYear = Integer.parseInt(carYearField.getText());
            if (carYear > Year.now().getValue() || carYear < 1870) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid year.");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid year.");
        }
        String carNumber = carNumberField.getText();

        if (carNumber == null || carYear == null || carModel == null) {
            throw new IllegalArgumentException("All fields must be filled.");
        }

        if (!carService.checkIllegal(carNumber)) return;

        if (carService.checkIfCarExist(carNumber)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Car already exists.");
            alert.showAndWait();
            return;
        }

        Car car = new Car();
        car.setModel(carModel);
        car.setNumber(carNumber.toUpperCase());
        car.setYear(carYear);

        carService.save(car);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Car added successfully.");
        alert.showAndWait();
    }

    @FXML
    public void goBack() {
        try {
            VBox repairForm = (VBox) springFXMLLoader.load("/fxml/repair_form.fxml");
            Scene scene = new Scene(repairForm);

            Stage stage = (Stage) carModelField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
            alert.show();
        }
    }

}
