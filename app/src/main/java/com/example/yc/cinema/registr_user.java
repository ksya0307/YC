package com.example.yc.cinema;

import android.text.Editable;
import android.util.Log;

import com.example.yc.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class registr_user {
    public Integer id;
    public String last_name;
    public String name;
    public String dad_name;
    public String login;
    public String password;
    public String email;
    public String phone;
    public String birthday;
    public Integer role;

    public void newUser(String last_name, String name, String login, String password, String email, String phone,String birthday, int role){
        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "INSERT INTO [dbo].[users] ([last_name],[name],[login],[password],[email],[phone],[birthday],[role]) values" +
                " ('"+last_name+"','"+name+"','"+login+"','"+password+"','"+email+"','"+phone+"',convert(datetime,'"+birthday+"',105),"+role+")";


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                int result = st.executeUpdate(sql);
                System.out.println("Новый пользователь добавлен");
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
