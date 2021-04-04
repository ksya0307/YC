package com.example.yc.cinema;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.yc.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class movies {

    public Map<Integer,String> getPopularMovies(){
        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select * from movies";
/*select movies.id , movies.movie from movies inner join shows on movies.id=shows.movie inner join tickets on shows.id=tickets.show \n" +
                "group by movies.movie, movies.id\n" +
                "having count(tickets.show)>10*/
        Map<Integer,String> movies = new TreeMap<Integer, String>();


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs!=null){
                    while(rs.next()){
                        movies.put(rs.getInt("id"),rs.getString("movie"));

                    }
                    return movies;
                }
            }
            else Log.e("ERROR","Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR","FAILED lol "+e.getMessage().toString());

        }finally {
            if (connect != null) {
                System.out.println("Закрытие подключения");

                try {
                    connect.close();
                    return movies;
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        return null;
    }

    public Map<Integer,String> getMovies(){


        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select id, movie from movies";
        Map<Integer,String> movies = new TreeMap<Integer, String>();


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs!=null){
                    while(rs.next()){
                        movies.put(rs.getInt("id"),rs.getString("movie"));
                    }
                    return movies;
                }
            }
            else Log.e("ERROR","Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR","FAILED lol "+e.getMessage().toString());

        }finally {
            if (connect != null) {
                System.out.println("Закрытие подключения");

                try {
                    connect.close();
                    return movies;
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        return null;
    }
}
