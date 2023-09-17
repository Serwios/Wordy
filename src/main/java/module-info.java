module com.geekglasses.wordy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires lombok;
    requires aws.java.sdk.core;
    requires aws.java.sdk.translate;
    requires commons.csv;
    requires annotations;

    opens com.geekglasses.wordy to javafx.fxml;
    exports com.geekglasses.wordy;
    exports com.geekglasses.wordy.controller;
    opens com.geekglasses.wordy.controller to javafx.fxml;
}