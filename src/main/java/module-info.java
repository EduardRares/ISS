module ro.iss2025.medicineorderingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ro.iss2025.medicineorderingsystem to javafx.fxml;
    exports ro.iss2025.medicineorderingsystem;
    exports ro.iss2025.medicineorderingsystem.controller;
    opens ro.iss2025.medicineorderingsystem.controller to javafx.fxml;
}