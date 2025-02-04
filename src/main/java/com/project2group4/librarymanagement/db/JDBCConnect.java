package com.project2group4.librarymanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnect {
    
    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;
    public static String DBNAME = "manage_library";
    public static String USERNAME = "admin";
    public static String PASSWORD = "admin";
    
    public static Connection getJDBCConnection(){
        Connection con = null;
        String connectionURL = "jdbc:postgresql://localhost:5432/" + DBNAME;
        System.out.println(connectionURL);
        
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Where is your MySQL JDBC Driver?");
            return con;
        }
        System.out.println("MySQL JDBC Driver Registered!");
        
        try {
            con = DriverManager.getConnection(connectionURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Connection Failed! Check output console");
            return con;
        }
        
        return con;
    }
    
    public static void closeConnection(Connection con){
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Connection fails");
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Close ResultSet fails");
        }
    }
    
    public static void closePreparedStatement(PreparedStatement prepare){
        try {
            if (prepare != null) {
                prepare.close();
            }
        } catch (SQLException e) {
            System.out.println("Close PreparedStatement fails");
        }
    }
    
    public static void main(String[] args) {
        System.out.println(JDBCConnect.getJDBCConnection());
    }
}
