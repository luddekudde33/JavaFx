module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;



    requires com.dlsc.formsfx;

    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
    exports com.example.demo2.controller;
    opens com.example.demo2.controller to javafx.fxml;
    exports com.example.demo2.Dao;
    opens com.example.demo2.Dao to javafx.fxml;
    exports com.example.demo2.Model;
    opens com.example.demo2.Model to javafx.fxml;
    exports com.example.demo2.Sql;
    opens com.example.demo2.Sql to javafx.fxml;
}