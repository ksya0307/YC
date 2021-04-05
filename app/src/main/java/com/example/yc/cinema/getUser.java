package com.example.yc.cinema;

import android.util.Log;

import androidx.fragment.app.DialogFragment;

import com.example.yc.sha253pwd;
import com.example.yc.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class getUser extends DialogFragment {
    public String login;
    public String password;
    public Boolean getUser(String login) {
        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select id from users where login='" + login + "'";

        Boolean existingUser =  false;
        try {
            if (connect != null) {
                Log.e("OK", "Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println(rs);
                int id = 0;
                if (rs != null) {
                    while(rs.next()){
                        id = rs.getInt("id");
                        System.out.println(id);
                    }
                    if(id!=0){
                        existingUser = true;
                    }

                   //если есть юзер
                }
            } else Log.e("ERROR", "Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR", "FAILED lol " + e.getMessage().toString());

        } finally {
            if (connect != null) {
                System.out.println("Закрытие подключения");
                try {
                    connect.close();
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        System.out.println(existingUser);
        return existingUser;
    }

    //ДОПИСАТЬ АВТОРИЗАЦИЮ
    public Boolean authUser(String login,String password) throws Exception {
        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select id from users where login='" + login + "' and password='"+ sha253pwd.encrypt(password)+"'";

        Boolean existingUser =  false;
        try {
            if (connect != null) {
                Log.e("OK", "Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println(rs);
                int id = 0;
                if (rs != null) {
                    while(rs.next()){
                        id = rs.getInt("id");
                        System.out.println(id);
                    }
                    if(id!=0){
                        existingUser = true;
                    }
                }
            } else Log.e("ERROR", "Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR", "FAILED lol " + e.getMessage().toString());

        } finally {
            if (connect != null) {
                System.out.println("Закрытие подключения");
                try {
                    connect.close();
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        System.out.println(existingUser);
        return existingUser;
    }

}
