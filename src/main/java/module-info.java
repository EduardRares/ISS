module ro.iss2025.medicineorderingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires java.desktop; // Required by Hibernate for some type mappings or JNDI
    requires jakarta.persistence; // JPA API
    requires org.hibernate.orm.core; // Hibernate ORM
    requires spring.context; // If you are using Spring context
    requires java.naming; // Required by Hibernate for JNDI, even if not directly used

    // JAXB module - Required by Hibernate
    requires jakarta.xml.bind;

    // Required by Hibernate Validator
    requires jakarta.validation;


    // Hibernate uses jboss-logging
    requires org.jboss.logging;
    // Jakarta Transaction API
    requires jakarta.transaction;
    requires jakarta.enterprise.cdi;

    opens ro.iss2025.medicineorderingsystem to javafx.fxml;
    exports ro.iss2025.medicineorderingsystem;
    exports ro.iss2025.medicineorderingsystem.controller;
    opens ro.iss2025.medicineorderingsystem.controller to javafx.fxml;
    opens ro.iss2025.medicineorderingsystem.domain to org.hibernate.orm.core, javafx.base;

}