package com.example.yc;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    @SuppressLint("NewApi")
    public static Connection conn(){
        String user = "sa";
        String pass="123";
        String db="YummyCinema";
        String port="1433";
        String ip="192.168.0.107";
        String instance="KSYA\\SERVER2021";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connect = null;
        String ConnURL=null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL="jdbc:jtds:sqlserver://"+ip+":"+port+"/"+instance+";databaseName="+db+";user="+user+";password="+pass+";";
            connect= DriverManager.getConnection(ConnURL);
        } catch (ClassNotFoundException | SQLException e) {
            Log.e("ERROR",e.getMessage());
        }
        return connect;
    }
}
