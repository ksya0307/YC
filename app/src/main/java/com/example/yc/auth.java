package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yc.cinema.getUser;

public class auth extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.yc.MESSAGE";

    public EditText login,password;
    public CheckBox showpwd;
    public Button auth;
    public boolean success;
    TextView user_number;
    Button main;
    Button user_tickets;
    Button user_bookings;
    Button user_edit;
    Button exit;
    public String name;
    public String last_name;
    public String user_login;
    public String birthday;
    public String phone;
    public String email;
    DrawerLayout drawerLayout;
    public String sms = "cant";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        login =findViewById(R.id.login_input_auth);
        auth = findViewById(R.id.auth_btn);
        password = findViewById(R.id.password_input_auth);
        showpwd = findViewById(R.id.show_pwd);

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


        showpwd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        auth.setOnClickListener(v -> {
            getUser authUser = new getUser();


            try {
                //проверяем есть ли такой юзер
                this.success = authUser.authUser(login.getText().toString(),password.getText().toString());

                System.out.println(success);
                if (!success){
                    Toast.makeText(com.example.yc.auth.this,"Неверный логин или пароль",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(com.example.yc.auth.this,"Успешно!",Toast.LENGTH_LONG).show();
                    this.last_name = authUser.last_name;
                    this.name = authUser.name;
                    this.user_login = authUser.login;
                    this.birthday = authUser.birthday;
                    this.phone = authUser.phone;
                    this.email = authUser.email;
                    this.success = true;
                    Intent intent = new Intent(this, MainActivity.class);
                    sms = "can";
                    intent.putExtra("last_name",this.last_name);
                    intent.putExtra("name",this.name);
                    intent.putExtra("login",this.user_login);
                    intent.putExtra("birthday",this.birthday);
                    intent.putExtra("phone",this.phone);
                    intent.putExtra("email",this.email);
                    intent.putExtra("sms",this.sms);
                    startActivity(intent);
                    login.getText().clear();
                    password.getText().clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void logUp(View view) {
        redirectActivity(this,registr.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void LogIn(View view){
        recreate();
    }
    public void toMain(View view){
        redirectActivity(this, MainActivity.class);
    }
}