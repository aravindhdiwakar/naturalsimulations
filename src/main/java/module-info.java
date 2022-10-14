module com.example.naturalsimulations {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.naturalsimulations to javafx.fxml;
    exports com.example.naturalsimulations;
}