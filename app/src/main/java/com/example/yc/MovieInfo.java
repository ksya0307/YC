package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import static android.content.ContentValues.TAG;

public class MovieInfo extends AppCompatActivity {

    public LinearLayout second, fortimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        second= findViewById(R.id.second_lay);
        fortimes =findViewById(R.id.fortimes);
        getIncomingIntent();


    }

    private void getIncomingIntent(){
        Log.d(TAG,"чекни исходящие интенты");
        if(getIntent().hasExtra("movie_name") && getIntent().hasExtra("genre") && getIntent().hasExtra("descr") && getIntent().hasExtra("img")){
            Log.d(TAG," интенты есть");
            String img_url = getIntent().getStringExtra("img");
            String name_movie = getIntent().getStringExtra("movie_name");
            String genre = getIntent().getStringExtra("genre");
            String description = getIntent().getStringExtra("descr");
            setImage(img_url,name_movie,genre,description);


///////////////////
            Statement st = null;
            connection con = new connection();
            Connection connect = connection.conn();
            String sql = "select distinct shows.date from shows inner join movies " +
                    "on shows.movie=movies.id where movies.movie='"+name_movie+"'";
            Button movie_dates;



            LinearLayout.LayoutParams for_dates_btn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            try {
                if(connect!=null) {
                    Log.e("OK","Коннект");
                    st = connect.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    if(rs!=null) {
                        while (rs.next()) {
                            movie_dates = new Button(this);
                            movie_dates.setText(rs.getString("date"));
                            movie_dates.setOnClickListener(getTimes(movie_dates.getText().toString(),name_movie.toString()));
                            second.addView(movie_dates, for_dates_btn);


                        }

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

                    } catch (SQLException e) {
                        System.out.println(e.toString() + "check");
                    }
                }
            }
        }
}
    View.OnClickListener chooseSeat(final String date, final String movie, final String time){
        return v->{
            Statement st = null;
            connection con = new connection();
            Connection connect = connection.conn();

            String sql = "select shows.cost, shows.id, FORMAT(cast(shows.start_time AS TIME), N'hh\\:mm'), shows.zal from shows inner join movies  " +
                    "on shows.movie=movies.id where movies.movie='"+movie+"' and shows.date='"+ date+"' and shows.start_time='"+time+"'";

            try {
                if(connect!=null) {
                    Log.e("OK","Коннект");
                    st = connect.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    fortimes.removeAllViews();
                    if(rs!=null){
                        while(rs.next()){

                            Intent intent = new Intent(this,SelectSeat.class);
                            intent.putExtra("name",movie);
                            intent.putExtra("zal", rs.getString("zal"));
                            intent.putExtra("date",date);
                            intent.putExtra("time", time);
                            intent.putExtra("cost",rs.getString("cost"));
                            intent.putExtra("idshow",rs.getString("id"));
                            startActivity(intent);

                        }

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
                    } catch (SQLException e) {
                        System.out.println(e.toString() + "check");
                    }
                }
            }
        };
    }

    View.OnClickListener getTimes(final String date, final String movie) {
        return v -> {

            Statement st = null;
            connection con = new connection();
            Connection connect = connection.conn();

            String sql = "select shows.start_time from shows inner join movies  on shows.movie=movies.id where movies.movie='"+movie+"' and shows.date='"+ date+"'";

            try {
                if(connect!=null) {
                    Log.e("OK","Коннект");
                    st = connect.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    fortimes.removeAllViews();
                    if(rs!=null){
                        while(rs.next()){
                                Button times = new Button(this);
                                times.setText(rs.getString("start_time"));
                                    fortimes.addView(times);
                                    times.setOnClickListener(chooseSeat(date,movie,times.getText().toString()));
                        }

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
                    } catch (SQLException e) {
                        System.out.println(e.toString() + "check");
                    }
                }
            }
        };
    }

    private  void  setImage(String img_url, String name, String genre, String description){
        Log.d(TAG,"устанавливаем имя и т.д");

        TextView p_name = findViewById(R.id.p_name);
        p_name.setText(name.trim());

        TextView p_genre = findViewById(R.id.p_genre);
        p_genre.setText(genre.trim());

        TextView p_descr = findViewById(R.id.p_movie_desc);
        p_descr.setText(description.trim());
        ImageView p_img = findViewById(R.id.p_img);
        Glide.with(this)
                .asBitmap()
                .load(img_url)
                .into(p_img);

    }
}