package com.swingy.database;

import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnect {

    public Connection connect() {
        return getConnect();
    }

    @Nullable
    private Connection getConnect() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:Swingy.db";
            connection = DriverManager.getConnection( url );
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        }
        return connection;
    }
}
