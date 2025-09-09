module se233.chapter4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens se233.chapter4 to javafx.fxml;
    exports se233.chapter4;
}