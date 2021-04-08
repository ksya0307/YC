package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yc.cinema.addticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.graphics.Color.parseColor;

public class SelectSeat extends AppCompatActivity {

    GridLayout mainGrid;
    //стоимость
    int cost =0;
    int numtickets=0;
    //выбрано билетов
    Button totaltickets;
    //купить
    private Button buy;

    //забронировать
    private Button book;
    public String idshow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);


        mainGrid = findViewById(R.id.main_grid);
        getIncomingIntent();

        buy = findViewById(R.id.buytickets);
        buy.setOnClickListener(ClickBuy(Integer.parseInt(idshow)));


        book = findViewById(R.id.bookTickets);

        totaltickets = findViewById(R.id.num_tickets);
        com.example.yc.cinema.addticket addticket = new addticket();

        setToggleEvent(mainGrid);

    }

    View.OnClickListener ClickBuy(int idshow){
        return v -> {
            boolean bSelected = false;
            int iSeatNum;
            for(int i=0;i<mainGrid.getChildCount();i++){
                final CardView seat = (CardView) mainGrid.getChildAt(i);
                if(seat.getCardBackgroundColor().getDefaultColor() == -1949110)
                {
                    bSelected=true;
                    break;
                }
            }
            if(!bSelected) {
                Toast.makeText(this, "Выберите как минимум одно место", Toast.LENGTH_SHORT).show();
            }else{
                for(int i=0;i<mainGrid.getChildCount();i++){
                    final CardView seat = (CardView) mainGrid.getChildAt(i);
                    if ( seat.getCardBackgroundColor().getDefaultColor() ==-1949110){
                        iSeatNum = seat.getId();
                        String idNum = getResources().getResourceName(iSeatNum).replaceAll("com.example.yc:id/s","");
                        int idseat= Integer.parseInt(idNum);

                        addticket.addticket(idshow,idseat,6);

                        seat.setCardBackgroundColor(parseColor("#D12029"));
                    }
                    if(seat.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#D12029")) seat.setEnabled(false);
                }
                successSMS();
                totaltickets.setText("билеты");
            }
        };
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("name")&&
                getIntent().hasExtra("zal")&&
                getIntent().hasExtra("date")&&
                getIntent().hasExtra("time")&&
                getIntent().hasExtra("cost")&&
                getIntent().hasExtra("idshow")){
            String movie_name = getIntent().getStringExtra("name");
            String zal = getIntent().getStringExtra("zal");
            String date = getIntent().getStringExtra("date");
            String time = getIntent().getStringExtra("time");
            String cost = getIntent().getStringExtra("cost");
            idshow = getIntent().getStringExtra("idshow");

            setShow(movie_name,zal,date,time,cost);

            UpdateTickets(Integer.parseInt(idshow));
            UpdateBookings(Integer.parseInt(idshow));


        }
    }

    private void setShow(String movie_name,String zal,String date,String time,String cost){
        TextView name, show_zal, show_date, show_time, show_cost;
        name = findViewById(R.id.choosed_movie_name);
        show_zal = findViewById(R.id.id_zal);
        show_date = findViewById(R.id.show_date);
        show_time = findViewById(R.id.show_time);
        show_cost = findViewById(R.id.show_cost);

        name.setText(movie_name.trim());
        show_zal.setText("Зал:"+zal);
        show_date.setText(date);
        show_time.setText(time);
        show_cost.setText(cost);
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor", "SetTextI18n"})
    private void setToggleEvent(GridLayout mainGrid){
        for(int i=0; i<mainGrid.getChildCount(); i++){

            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            if((cardView.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#7BFE77")
                    || cardView.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#E2424A"))
                    && cardView.getCardBackgroundColor().getDefaultColor() != Color.parseColor("#D12029")) {

                cardView.setOnClickListener(v -> {

                    if( cardView.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#7BFE77")){//зеленый
                        System.out.println(cardView.getCardBackgroundColor()+"");
                        cardView.setCardBackgroundColor(parseColor("#E2424A"));//красный
                        System.out.println(cardView.getCardBackgroundColor()+"");
                        ++numtickets;

                    }else{
                        cardView.setCardBackgroundColor(parseColor("#7BFE77"));//зеленый
                        --numtickets;

                    }
                    totaltickets.setText(numtickets+"");
                });
            }

        }
    }

    private void UpdateBookings(int show){

        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select bookingID, customer,seat,show from bookings where show="+show;

        try {
            if (connect != null) {
                Log.e("OK", "Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (rs != null) {
                    while(rs.next()){
                        for (int i=0;i<mainGrid.getChildCount();i++){

                            final CardView cardView = (CardView) mainGrid.getChildAt(i);
                            int idcardview = Integer.parseInt(cardView.getResources().getResourceName(cardView.getId()).replaceAll("com.example.yc:id/s",""));
                            int seat = rs.getInt("seat");
                            if(idcardview ==seat) cardView.setCardBackgroundColor(Color.parseColor("#FED477"));
                        }
                    }

                }
            } else Log.e("ERROR", "Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR", "FAILED lol " + e.getMessage());

        } finally {
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

    @SuppressLint("ResourceType")
    private void UpdateTickets(int show){
        Statement st = null;
        connection con = new connection();
        Connection connect = connection.conn();
        String sql = "select id,show,seat,customer from tickets where show="+show;

        try {
            if (connect != null) {
                Log.e("OK", "Коннект");
                st = connect.createStatement();
                ResultSet rs = st.executeQuery(sql);

                if (rs != null) {
                    while(rs.next()){
                        for (int i=0;i<mainGrid.getChildCount();i++){

                            final CardView cardView = (CardView) mainGrid.getChildAt(i);
                            int idcardview = Integer.parseInt(cardView.getResources().getResourceName(cardView.getId()).replaceAll("com.example.yc:id/s",""));
                            int seat = rs.getInt("seat");
                            if(idcardview ==seat) cardView.setCardBackgroundColor(Color.parseColor("#D12029"));
                        }
                    }

                }
            } else Log.e("ERROR", "Ошибочка");

        } catch (SQLException e) {
            Log.e("ERROR", "FAILED lol " + e.getMessage());

        } finally {
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

    public  void successSMS(){
        SuccessBuy purchase = new SuccessBuy();
        purchase.show(getSupportFragmentManager(),"Успешная покупка");

    }
}
