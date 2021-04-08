package com.example.yc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.yc.cinema.Movie;
import com.example.yc.cinema.genres;
import com.example.yc.cinema.getUser;
import com.example.yc.cinema.movies;
import com.squareup.picasso.Target;


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
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<Movie> movieList;
    public String name, last_name, user_login, birthday, phone, email, sms = "cant";

    public movies movie;

    public String get_genres;
    public LinearLayout linearLayoutGenres;
    public LinearLayout linearLayoutPopularMovies;
    public LinearLayout linearLayoutAllMovies;

    TextView user_number, login_lbl;
    Button main;
    Button user_tickets;
    Button user_bookings;
    Button user_edit;
    Button exit;

    DrawerLayout drawerLayout;

    @SuppressLint({"ResourceAsColor", "SetTextI18n", "ResourceType", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        user_number = findViewById(R.id.user_login);
        user_number.setVisibility(View.INVISIBLE);

        main = findViewById(R.id.main_win);
        //main.setVisibility(View.INVISIBLE);

        user_tickets = findViewById(R.id.user_tickets);
        user_tickets.setVisibility(View.INVISIBLE);

        user_bookings = findViewById(R.id.user_bookings);
        user_bookings.setVisibility(View.INVISIBLE);

        user_edit = findViewById(R.id.user_edit);
        user_edit.setVisibility(View.INVISIBLE);

        exit = findViewById(R.id.user_logout);
        exit.setVisibility(View.INVISIBLE);

        login_lbl = findViewById(R.id.login_lbl);


        user_edit.setOnClickListener(v -> {
            Intent intent = new Intent(this,UserProfile.class);
            intent.putExtra("last_name",this.last_name);
            intent.putExtra("name",this.name);
            intent.putExtra("login",this.user_login);
            intent.putExtra("birthday",this.birthday);
            intent.putExtra("phone",this.phone);
            intent.putExtra("email",this.email);
            intent.putExtra("sms",this.sms);
            startActivity(intent);
        });


        if (getIntent().hasExtra("last_name") &&
                getIntent().hasExtra("name") &&
                getIntent().hasExtra("login") &&
                getIntent().hasExtra("birthday") &&
                getIntent().hasExtra("phone") &&
                getIntent().hasExtra("email") &&
                getIntent().hasExtra("sms")
        ) {


            last_name =  getIntent().getStringExtra("last_name");
            name =  getIntent().getStringExtra("name");
            user_login =  getIntent().getStringExtra("login");
            birthday =  getIntent().getStringExtra("birthday");
            phone =  getIntent().getStringExtra("phone");
            email =  getIntent().getStringExtra("email");
            sms =  getIntent().getStringExtra("sms");


            System.out.println(sms + " Чо тут у нас\n" + phone);

            if (sms.equals("can")) {
                user_number.setVisibility(View.VISIBLE);
                user_number.setText(user_login.trim());

                main.setVisibility(View.VISIBLE);
                user_tickets.setVisibility(View.VISIBLE);
                user_bookings.setVisibility(View.VISIBLE);
                user_edit.setVisibility(View.VISIBLE);
                exit.setVisibility(View.VISIBLE);

                login_lbl.setVisibility(View.INVISIBLE);
            }
        }


        drawerLayout = findViewById(R.id.drawer_layout);

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


        //жанры
        Map<Integer, String> genres;
        com.example.yc.cinema.genres genre = new genres();
        genres = genre.getGenres();

        //эмоджи жанров
        ArrayList<String> pics;
        pics = genre.getPics();

        //количество
        int getsize = pics.size();
        System.out.println("колво" + getsize);


        //шрифт
        Typeface genre_font = ResourcesCompat.getFont(this, R.font.rubik_light);


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

            System.out.println(genre_item.getId());
            size++;
            genre_item.setOnClickListener(handleOnClick(genre_item));
            linearLayoutGenres.addView(genre_item);


        }
        all_genre.setOnClickListener(v -> {
            getMoviesInfo();
        });


        movie = new movies();



        Typeface movie_font = ResourcesCompat.getFont(this, R.font.rubik_regular);

        linearLayoutPopularMovies = (LinearLayout) findViewById(R.id.movies_db);

        LinearLayout.LayoutParams params_pop_movies = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_pop_movies.setMargins(10, 0, 0, 0);


        ////////////////////////////

        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select movies.id , movies.movie,movies.image_url from movies inner join shows on movies.id=shows.movie inner join tickets on shows.id=tickets.show " +
                "group by movies.movie, movies.id,image_url having count(tickets.show)>5 ";
        Map<Integer,String> movies = new TreeMap<Integer, String>();


        try {
            if(connect!=null) {
                Log.e("OK","Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if(rs!=null){
                    while(rs.next()){

                        Button movie_item = new Button(new ContextThemeWrapper(this, R.style.for_movies), null, R.style.for_movies);

                        movie_item.setTypeface(movie_font);
                        movie_item.setId(rs.getInt("id"));
                        movie_item.setWidth(197);
                        movie_item.setPadding(20, 20, 20, 0);

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(21));
                        Glide.with(MainActivity.this).load(rs.getString("image_url")).apply(requestOptions).into(new CustomTarget<Drawable>(179,269) {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                movie_item.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                movie_item.setCompoundDrawablesWithIntrinsicBounds(null, placeholder, null, null);
                            }
                        });


                        movie_item.setText(rs.getString("movie").trim());

                        linearLayoutPopularMovies.addView(movie_item, params_pop_movies);

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

        ////////////////



        getMoviesInfo();
}

     View.OnClickListener handleOnClick( final Button genre_item) {
        return v -> {
            search_result(genre_item.getText().toString());
            System.out.println(genre_item.getId());
        };
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

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickEdit(View view){
        redirectActivity(this,UserProfile.class);
    }

    public void LogIn(View view){
        redirectActivity(this,auth.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void ClickLogOut(View view){
        logout(this);
    }


    public void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Уведомление");
        builder.setMessage("Вы уверены, что хотите выйти?");
        builder.setPositiveButton("Да", (dialog, which) -> {
            //activity.finishAffinity();
            //System.exit(0);
            name="";
            last_name="";
            birthday="";
            phone="";
            user_login="";
            email="";
            user_number.setVisibility(View.INVISIBLE);
            user_tickets.setVisibility(View.INVISIBLE);
            user_bookings.setVisibility(View.INVISIBLE);
            exit.setVisibility(View.INVISIBLE);
            user_edit.setVisibility(View.INVISIBLE);
            login_lbl.setVisibility(View.VISIBLE);

        });
        builder.setNegativeButton("Нет", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public void toMain(View view){
       recreate();
    }

    private void getMoviesInfo(){

        Map<Integer, String> AllMovies;
        AllMovies = movie.getMovies();

        com.example.yc.cinema.genres get_genre = new genres();



        movieList = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : AllMovies.entrySet()) {
            get_genres = get_genre.getMovieGenre(entry.getKey());
            get_genres = get_genres.replaceAll(",$", "");
            get_genres = get_genres.replaceAll(",", ", ");

            String query ="select running_time,age,year, image_url, description from movies where id="+entry.getKey();
            Statement st = null;
            connection con = new connection();
            Connection connect = connection.conn();

            try {

                if(connect!=null){
                    st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    if(rs!=null){
                        while(rs.next()){
                            movieList.add(new Movie(entry.getValue(), get_genres,rs.getString("image_url"),rs.getInt("running_time") ,rs.getString("year"),rs.getInt("age"),rs.getString("description")));
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
}
