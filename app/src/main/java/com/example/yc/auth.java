package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.yc.cinema.getUser;

public class auth extends AppCompatActivity {

    public EditText login,password;
    public CheckBox showpwd;
    public Button auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        login =findViewById(R.id.login_input_auth);
        auth = findViewById(R.id.auth_btn);
        password = findViewById(R.id.password_input_auth);
        showpwd = findViewById(R.id.show_pwd);


        showpwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        auth.setOnClickListener(v -> {
            getUser authUser = new getUser();
            boolean success;
            try {
                success = authUser.authUser(login.getText().toString(),password.getText().toString());
                System.out.println(success);
                if (!success){
                    Toast.makeText(com.example.yc.auth.this,"Неверный логин или пароль",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(com.example.yc.auth.this,"Успешно!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
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
}