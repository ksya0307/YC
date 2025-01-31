package com.example.yc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yc.cinema.getUser;
import com.example.yc.cinema.registr_user;

public class registr extends AppCompatActivity {
    public EditText login;
    public EditText password;
    public EditText name;
    public EditText last_name;
    public EditText email;
    public EditText phone;
    public EditText birth;
    public EditText confirmpwd;

    public Button btn_newUser;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);

        login = findViewById(R.id.login_input);
        password = findViewById(R.id.pwd_input);
        confirmpwd = findViewById(R.id.conf_pwd);
        name = findViewById(R.id.name_input);
        last_name = findViewById(R.id.fam_input);
        phone = findViewById(R.id.phone_inp);
        email = findViewById(R.id.email_inp);
        birth = findViewById(R.id.birth_inp);

        btn_newUser = findViewById(R.id.registr_btn);

        com.example.yc.cinema.registr_user newUser = new registr_user();



        btn_newUser.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {


                com.example.yc.cinema.getUser existingUser = new getUser();
                Boolean exist = existingUser.getUser(login.getText().toString());
                if(exist){
                    openExistingDialog();
                }
                else if(login.getText().toString().equals("")
                        || password.getText().toString().equals("")
                         || confirmpwd.getText().toString().equals("")
                        || name.getText().toString().equals("")
                        || last_name.getText().toString().equals("")
                        || phone.getText().toString().equals("")
                        || email.getText().toString().equals("")
                        || birth.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Вы заполнили не все поля", Toast.LENGTH_LONG).show();
                }
                else if(password.getText().toString().equals(confirmpwd.getText().toString()))
                {
                    String encrpassword = null;
                    try {
                        encrpassword = sha253pwd.sha256(password.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    newUser.newUser(
                            last_name.getText().toString(),
                            name.getText().toString(),
                            login.getText().toString(),
                            encrpassword,
                            email.getText().toString(),
                            phone.getText().toString(),
                            birth.getText().toString(), 1);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Пароли не совпадают",Toast.LENGTH_LONG).show();
                }
        }
        });
    }

    public  void  openExistingDialog(){
    ErrorExist existUser = new ErrorExist();
        existUser.show(getSupportFragmentManager(),"Существующий пользователь");
    }

}