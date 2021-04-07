package com.example.yc.cinema;

import android.util.Log;

import com.example.yc.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class addticket {
    public void addticket(int idshow,int idseat,int iduser){
        PreparedStatement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "INSERT INTO tickets (show,seat,customer) values(" + idshow + "," + idseat + "," + iduser + ");";

        Map<Integer,String> genres = new TreeMap<Integer, String>();


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.prepareStatement(sql);
                st.executeUpdate();
            }
            else Log.e("ERROR","Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR","FAILED lol "+e.getMessage().toString());

        }finally {
            if (connect != null) {
                System.out.println("Закрытие подключения");

                try {
                    connect.close();
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
    }

}
