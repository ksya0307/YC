package com.example.yc.cinema;

import android.graphics.drawable.Drawable;


public class Movie {
    private String title;
    private String genres;
    private String image;
    private int time;
    private String year;
    private int age;
    private String right_arrow;

    public Movie(String title, String genres, String image, int time, String year, int age) {
        this.title = title;
        this.genres = genres;
        this.image = image;
        this.time = time;
        this.year = year;
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public String getGenres() {
        return genres;
    }

    public String getImage() {
        return image;
    }

    public int getTime() {
        return time;
    }

    public String getYear() {
        return year;
    }

    public int getAge() {
        return age;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
