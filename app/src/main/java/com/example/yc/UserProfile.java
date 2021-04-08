package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

public class UserProfile extends AppCompatActivity {

    EditText last_name,name, login, birthday, phone, email;

    DrawerLayout drawerLayout;

    TextView user_number, login_lbl;
    Button main;
    Button user_tickets;
    Button user_bookings;
    Button user_edit;
    Button exit;

    Button save_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profile);




        user_number = findViewById(R.id.user_login);
        user_number.setVisibility(View.VISIBLE);

        main = findViewById(R.id.main_win);
        //main.setVisibility(View.INVISIBLE);

        user_tickets = findViewById(R.id.user_tickets);
        user_tickets.setVisibility(View.VISIBLE);

        user_bookings = findViewById(R.id.user_bookings);
        user_bookings.setVisibility(View.VISIBLE);

        user_edit = findViewById(R.id.user_edit);
        user_edit.setVisibility(View.VISIBLE);

        exit = findViewById(R.id.user_logout);
        exit.setVisibility(View.VISIBLE);

        login_lbl = findViewById(R.id.login_lbl);
        login_lbl.setVisibility(View.INVISIBLE);

        drawerLayout = findViewById(R.id.drawer_layout);

        last_name = findViewById(R.id.profile_lastname);
        name = findViewById(R.id.profile_name);
        login = findViewById(R.id.profile_login);
        birthday = findViewById(R.id.profile_birthday);
        phone = findViewById(R.id.profile_phone);
        email = findViewById(R.id.profile_email);

        Intent intent = getIntent();
        last_name.setText(intent.getStringExtra("last_name").trim());
        name.setText(intent.getStringExtra("name").trim());
        login.setText(intent.getStringExtra("login").trim());
        birthday.setText(intent.getStringExtra("birthday").trim());
        phone.setText(intent.getStringExtra("phone").trim());
        email.setText(intent.getStringExtra("email").trim());

        user_number.setText(intent.getStringExtra("login").trim());


        save_data = findViewById(R.id.save_data);
        save_data.setOnClickListener(v -> {
            Statement st = null;
            connection con = new connection();
            Connection connect = connection.conn();
            String sql = "update users set last_name='"+last_name.getText().toString()+"', name='"+name.getText().toString()+
                    "', login='"+login.getText().toString()+"', phone='"+phone.getText().toString()+"', birthday='"+birthday.getText().toString()+"' where login='"+login.getText().toString()+"'";



            try {
                if(connect!=null) {
                    Log.e("OK","Коннект");
                    st = connect.createStatement();
                    st.executeUpdate(sql);
                    Toast.makeText(this,"Данные сохранены",Toast.LENGTH_SHORT).show();
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
        });
    }

    public void ClickLogOut(View view){
        logout(this);
    }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Уведомление");
        builder.setMessage("Вы уверены, что хотите выйти?");
        builder.setPositiveButton("Да", (dialog, which) -> {
            //activity.finishAffinity();
            //System.exit(0);

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
        redirectActivity(this,MainActivity.class);
    }

    public void ClickEdit(View view){
        recreate();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void logUp(View view) {
        redirectActivity(this,registr.class);
    }
}