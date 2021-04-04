package com.example.yc.cinema;

import android.util.Log;

import com.example.yc.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class genres {
    public int id;
    public String genre;
    public String pic;
    public Map<Integer,String> getGenres(){

        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select id, genre from genres";

        Map<Integer,String> genres = new TreeMap<Integer, String>();


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs!=null){
                    while(rs.next()){
                       genres.put(rs.getInt("id"),rs.getString("genre"));

                    }
                    return genres;
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
                    return genres;
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        return null;
    }

    public ArrayList<String> getPics(){

        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select pic from genres";

        ArrayList<String> pics = new ArrayList<String>();

        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs!=null){
                    while(rs.next()){
                        pics.add(rs.getString("pic"));

                    }
                    return pics;
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
                    return pics;
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        return null;
    }

    public String getMovieGenre(int id_movie){

        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select genres.genre from genres inner join movie_genres on genres.id=movie_genres.genre \n" +
                "where movie_genres.movie="+id_movie;

        StringBuilder genre =new StringBuilder();


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs!=null){
                    while(rs.next()){

                        genre.append(rs.getString("genre").trim()).append(",");
                    }
                    return genre.toString().toString();
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
                    return genre.toString().toString();
                } catch (SQLException e) {
                    System.out.println(e.toString() + "check");
                }
            }
        }
        return null;
    }
}
