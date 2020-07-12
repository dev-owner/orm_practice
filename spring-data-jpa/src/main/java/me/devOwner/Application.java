package me.devOwner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/springdata";
        String user = "cbbatte";
        String pass = "pass";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            System.out.println("connection = " + connection);
            String sql = "CREATE TABLE ACCOUNT (id int, username varchar(255), password varchar(255));";
            String insert_sql = "INSERT INTO ACCOUNT VALUES(1, 'jaewoo', 'pass')";
            try(PreparedStatement pstmt = connection.prepareStatement(insert_sql)) {
                pstmt.execute();
            }
        }
    }
}
