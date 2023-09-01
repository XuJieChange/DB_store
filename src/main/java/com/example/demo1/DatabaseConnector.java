package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static void main(String[] args){
        String url="jdbc:mariadb://localhost:3309/DB_store?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT";
        String user="root";
        String password="abc12345";

        try{
            Connection connection= DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database!!");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
