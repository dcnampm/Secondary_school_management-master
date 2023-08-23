module ltnc.qlthcs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ltnc.qlthcs to javafx.fxml;
    exports ltnc.qlthcs;
    exports ltnc.qlthcs.controllers;
    opens ltnc.qlthcs.controllers to javafx.fxml;
//    exports ltnc.qlthcs.view;
//    opens ltnc.qlthcs.view to javafx.fxml;
}