module com.example.ubisoftgames {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ubisoftgames to javafx.fxml;
    exports com.example.ubisoftgames;
}