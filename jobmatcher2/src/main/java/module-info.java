module com.example.jobmatcher2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.jobmatcher2 to javafx.fxml;
    exports com.example.jobmatcher2;
    exports com.example.jobagency;
    opens com.example.jobagency to javafx.fxml;
}