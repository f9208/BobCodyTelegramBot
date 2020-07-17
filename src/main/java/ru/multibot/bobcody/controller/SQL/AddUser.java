package ru.multibot.bobcody.controller.SQL;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@Setter
@Getter
@Component
//@ConfigurationProperties(prefix = "db")
public class AddUser {

    Connection connection;
    String urlDB = "jdbc:postgresql://localhost:5432/postgres";

    String userDB = "postgres";
    String passwordDB = "12345";


    public static void main(String[] args) {

        AddUser addUser = new AddUser();
        Statement statement;
        try {
            addUser.connection = DriverManager.getConnection(addUser.urlDB, addUser.userDB, addUser.passwordDB);
            statement = addUser.connection.createStatement();
            System.out.println("подконнектились");
            addUser.connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
