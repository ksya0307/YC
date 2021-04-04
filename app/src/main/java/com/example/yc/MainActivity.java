package com.example.yc;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yc.cinema.Movie;
import com.example.yc.cinema.genres;
import com.example.yc.cinema.movies;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Movie> movieList;


    public String get_genres;
    public LinearLayout linearLayoutGenres;
    public LinearLayout linearLayoutPopularMovies;
    public LinearLayout linearLayoutAllMovies;


    @SuppressLint({"ResourceAsColor", "SetTextI18n", "ResourceType", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        linearLayoutAllMovies = (LinearLayout) findViewById(R.id.all_movies);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EditText search_inp = findViewById(R.id.search);
        search_inp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        // @SuppressLint("UseCompatLoadingForDrawables") Drawable image_movie = this.getResources().getDrawable(R.drawable.ourfriend);
        //@SuppressLint("UseCompatLoadingForDrawables") Drawable arrow = this.getResources().getDrawable(R.drawable.right_arrow_main);

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        //params.gravity = Gravity.BOTTOM&Gravity.CENTER_HORIZONTAL;
//        params.setMargins(20,0,0,0);
//        params.setMarginEnd(20);
        //жанры
        Map<Integer, String> genres;
        com.example.yc.cinema.genres genre = new genres();
        genres = genre.getGenres();

        //эмоджи жанров
        ArrayList<String> pics;
        pics = genre.getPics();

        //количество
        int getsize = pics.size();
        System.out.println("колво"+getsize);

        //int count_genres = genre.getGenres().size();

        //шрифт
        Typeface genre_font = ResourcesCompat.getFont(this, R.font.rubik_light);

        //ContextThemeWrapper style_btn = new ContextThemeWrapper(this,R.style.for_genres);


        linearLayoutGenres = (LinearLayout) findViewById(R.id.genres_db);

        Button all_genre = new Button(this);
        LinearLayout.LayoutParams params_all_genre = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_all_genre.setMargins(10, 0, 0, 0);

        all_genre.setText("Все");
        all_genre.setTypeface(genre_font);
        all_genre.setAllCaps(false);
        all_genre.setTextColor(Color.WHITE);
        all_genre.setBackgroundResource(R.drawable.genre);
        all_genre.setTextSize(15);
        linearLayoutGenres.addView(all_genre, params_all_genre);//,params_all_genre

        int size = 0;

        for (Map.Entry<Integer, String> entry : genres.entrySet()) {
            //карточки жанров
            Button genre_item = new Button(new ContextThemeWrapper(this, R.style.for_genres), null, R.style.for_genres);
            int id = getResources().getIdentifier(pics.get(size), "drawable", getPackageName());

            genre_item.setCompoundDrawablesRelativeWithIntrinsicBounds(0, id, 0, 0);
            genre_item.setId(entry.getKey());
            genre_item.setText(entry.getValue().trim());
            genre_item.setTypeface(genre_font);
//                System.out.println(pics.get(size) + " " + entry.getKey());
            System.out.println(genre_item.getId());
            size++;
            genre_item.setOnClickListener(handleOnClick(genre_item));
            linearLayoutGenres.addView(genre_item);


        }


        Map<Integer, String> PopularMovies;
        movies movie = new movies();
        PopularMovies = movie.getPopularMovies();


        Typeface movie_font = ResourcesCompat.getFont(this, R.font.rubik_regular);

        linearLayoutPopularMovies = (LinearLayout) findViewById(R.id.movies_db);

        LinearLayout.LayoutParams params_pop_movies = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_pop_movies.setMargins(10, 0, 0, 0);


        for (Map.Entry<Integer, String> entry : PopularMovies.entrySet()) {
            Button movie_item = new Button(new ContextThemeWrapper(this, R.style.for_movies), null, R.style.for_movies);

            movie_item.setTypeface(movie_font);
            movie_item.setId(entry.getKey());
            movie_item.setWidth(197);
            movie_item.setHeight(363);
            System.out.println(entry.getKey() + "\n");
            movie_item.setPadding(25, 20, 25, 35);
            movie_item.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.soul, 0, 0);

            movie_item.setText(entry.getValue().trim());
            //+System.getProperty("line.separator")
            linearLayoutPopularMovies.addView(movie_item, params_pop_movies);
        }


        Map<Integer, String> AllMovies;
        AllMovies = movie.getMovies();

        com.example.yc.cinema.genres get_genre = new genres();

        LinearLayout.LayoutParams params_for_all_movies = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_for_all_movies.topMargin = 10;
        params_for_all_movies.gravity = Gravity.CENTER;

//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(movieList,MainActivity.this);

//
        movieList = new ArrayList<>();

         for (Map.Entry<Integer, String> entry : AllMovies.entrySet()) {
            get_genres = get_genre.getMovieGenre(entry.getKey());
            get_genres = get_genres.replaceAll(",$", "");
            get_genres = get_genres.replaceAll(",", ", ");

             String query ="select running_time,age,year, image_url from movies where id="+entry.getKey();
            Statement st = null;
            connection con = new connection();
            Connection connect = connection.conn();

            try {

                if(connect!=null){
                    st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    if(rs!=null){
                        while(rs.next()){
                            movieList.add(new Movie(entry.getValue(), get_genres,rs.getString("image_url"),rs.getInt("running_time") ,rs.getString("year"),rs.getInt("age")));
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
        mAdapter = new RecyclerViewAdapter(movieList,MainActivity.this);
        recyclerView.setAdapter(mAdapter);

    }

     View.OnClickListener handleOnClick( final Button genre_item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_result(genre_item.getText().toString());
                System.out.println(genre_item.getId());
            }
        };
    }

    private View.OnClickListener clickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickedGenre();
        }
    };
    private void clickedGenre(){
//
//        search_result(genre_item.getText().toString());
//        System.out.println(genre_item.getId());

    }
    private void filter(String movie){
        ArrayList<Movie> filteredMoviesList = new ArrayList<>();
        for(Movie item: movieList){
           if( item.getTitle().toLowerCase().contains(movie.toLowerCase())){
                filteredMoviesList.add(item);
            }
        }
        mAdapter.filterList(filteredMoviesList);
    }

    private void search_result(String genre){
        ArrayList<Movie> choosedGenre = new ArrayList<>();
        for(Movie item:movieList){
            if(item.getGenres().toLowerCase().contains(genre.toLowerCase())){
                choosedGenre.add(item);
            }
        }
        mAdapter.choosedGenreList(choosedGenre);
    }
    //загитено
    //адд комент

}
