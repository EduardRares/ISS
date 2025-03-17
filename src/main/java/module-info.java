module ro.iss2025.medicineorderingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens ro.iss2025.medicineorderingsystem to javafx.fxml;
    exports ro.iss2025.medicineorderingsystem;
    exports ro.iss2025.medicineorderingsystem.controller;
    opens ro.iss2025.medicineorderingsystem.controller to javafx.fxml;
    opens ro.iss2025.medicineorderingsystem.domain to javafx.base;

}