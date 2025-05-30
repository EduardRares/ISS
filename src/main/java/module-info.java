module ro.iss2025.medicineorderingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires java.sql;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires spring.context;
    opens ro.iss2025.medicineorderingsystem to javafx.fxml;
    exports ro.iss2025.medicineorderingsystem;
    exports ro.iss2025.medicineorderingsystem.controller;
    opens ro.iss2025.medicineorderingsystem.controller to javafx.fxml;
    opens ro.iss2025.medicineorderingsystem.domain to org.hibernate.orm.core, javafx.base;

}