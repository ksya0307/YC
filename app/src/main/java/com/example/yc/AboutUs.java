package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {


    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_us);
        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void ClickMenu(View view){
        NavDrawer.openDrawer(drawerLayout);
    }
    public  void ClickLogo(View view){
        NavDrawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        NavDrawer.redirectActivity(this,NavDrawer.class);
    }
    public void ClickDashboard(View view){
        NavDrawer.redirectActivity(this,Dashboard.class);
    }
    public void ClickAboutUs(View view){
        recreate();

    }
    public void ClickLogout(View view){
        NavDrawer.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawer.closeDrawer(drawerLayout);
    }
}