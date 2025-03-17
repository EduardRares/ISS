package ro.iss2025.medicineorderingsystem.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Privilege;
import ro.iss2025.medicineorderingsystem.utils.events.HospitalEventType;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEvent;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEventType;

import java.util.Optional;

public class TakeInfoAlert {
    static Optional<Medicine> takeInfoMedicine(Medicine medicine, MedicineEventType event) {
        Label name = new Label();
        name.setText("Name");
        Label type = new Label();
        type.setText("Type");
        Label description = new Label();
        description.setText("Description");
        TextField nameField = new TextField();
        TextField typeField = new TextField();
        TextField descriptionField = new TextField();
        Button submitButton = new Button();
        submitButton.setText("Add");
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 400, 300);
        if(event == MedicineEventType.UPDATE) {
            nameField.setText(medicine.getName());
            typeField.setText(medicine.getType());
            descriptionField.setText(medicine.getDescription());
            submitButton.setText("Update");
        }
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(name, nameField, new Label(), type, typeField, new Label(), description, descriptionField, new Label(), submitButton);
        Stage stage = new Stage();
        stage.setScene(scene);
        final Optional<Medicine>[] result = new Optional[]{Optional.empty()};

        submitButton.setOnAction(e -> {
            medicine.setName(nameField.getText());
            medicine.setType(typeField.getText());
            medicine.setDescription(descriptionField.getText());
            result[0] = Optional.of(medicine);
            stage.close();
        });

        stage.showAndWait();
        return result[0];
    }

    static Optional<HospitalEmployee> takeInfoEmployee(HospitalEmployee employee, HospitalEventType event) {
        Label name = new Label();
        name.setText("email");
        Label type = new Label();
        type.setText("privilege");
        TextField emailField = new TextField();
        TextField privilegeField = new TextField();
        Button submitButton = new Button();
        submitButton.setText("Add");
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 400, 300);
        if(event == HospitalEventType.UPDATE) {
            emailField.setText(employee.getEmail());
            privilegeField.setText(employee.getPrivilege().toString());
            submitButton.setText("Update");
        }
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(name, emailField, new Label(), type, privilegeField,new Label(), submitButton);
        Stage stage = new Stage();
        stage.setScene(scene);
        final Optional<HospitalEmployee>[] result = new Optional[]{Optional.empty()};

        submitButton.setOnAction(e -> {
            employee.setEmail(emailField.getText());
            if("ADMIN".equals(employee.getPrivilege())) {
                employee.setPrivilege(Privilege.ADMIN);
            }
            else if("MEDICAL_STAFF".equals(employee.getPrivilege())) {
                employee.setPrivilege(Privilege.MEDICAL_STAFF);
            }
            else if("PHARMACIST".equals(employee.getPrivilege())) {
                employee.setPrivilege(Privilege.PHARMACIST);
            }
            result[0] = Optional.of(employee);
            stage.close();
        });

        stage.showAndWait();
        return result[0];
    }
}
