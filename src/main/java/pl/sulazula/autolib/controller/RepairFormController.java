package pl.sulazula.autolib.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sulazula.autolib.impl.SpringFXMLLoader;
import pl.sulazula.autolib.model.Repair;
import pl.sulazula.autolib.service.CarService;


import java.time.LocalDate;
import java.util.Objects;

@Component
public class RepairFormController {

    @FXML private TextField carNumberField;
    @FXML private TextField descField;
    @FXML private DatePicker datePicker;
    @FXML private Button openAddFormButton;

    private final CarService carService;

    @Autowired
    private final SpringFXMLLoader springFXMLLoader;

    @Autowired
    public RepairFormController(CarService carService, SpringFXMLLoader springFXMLLoader) {
        this.carService = carService;
        this.springFXMLLoader = springFXMLLoader;
    }

    @FXML
    public void addRepair() {
        try {
            String carNumber = carNumberField.getText();
            String desc = descField.getText();
            LocalDate date = datePicker.getValue();

            if (desc == null || date == null || carNumber == null) {
                throw new IllegalArgumentException("All fields must be filled");
            }

            carService.setNewRepair(carNumber, desc, date);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "New info added!");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void openAddFormScene(ActionEvent event) {
        try {
            VBox addCarForm = (VBox) springFXMLLoader.load("/fxml/add_car.fxml");

            Scene scene = new Scene(addCarForm);

            Stage stage = (Stage) openAddFormButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error: " + e.getMessage() + "\n" + e.getCause());
            alert.show();
        }

    }


}
