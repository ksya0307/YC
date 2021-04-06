package com.example.yc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yc.cinema.addticket;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);


        mainGrid = (GridLayout) findViewById(R.id.main_grid);
        buy = (Button) findViewById(R.id.buytickets);
        book = (Button) findViewById(R.id.bookTickets);

        totaltickets = (Button) findViewById(R.id.num_tickets);
        com.example.yc.cinema.addticket addticket = new addticket();

        buy.setOnClickListener(v -> {
            boolean bSelected = false;
            int iSeatNum=0;
            for(int i=0;i<mainGrid.getChildCount();i++){
                final CardView seat = (CardView) mainGrid.getChildAt(i);
                if(seat.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#E2424A")){
                    iSeatNum = seat.getId();
                    String idNum = getResources().getResourceName(iSeatNum);
                    int idseat= Integer.parseInt(idNum.replaceAll("id/s",""));
                    System.out.println(idseat+"");
                    //addticket.addticket(5,idseat,6);//
                    //
                }
            }
        });

        setToggleEvent(mainGrid);
    }
    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceAsColor", "SetTextI18n"})
    private void setToggleEvent(GridLayout mainGrid){
        for(int i=0; i<mainGrid.getChildCount(); i++){

            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(v -> {

                if( cardView.getCardBackgroundColor().getDefaultColor() == Color.parseColor("#7BFE77")){
                   cardView.setCardBackgroundColor(parseColor("#E2424A"));

                    ++numtickets;


                }else{
                    cardView.setCardBackgroundColor(parseColor("#7BFE77"));
                    --numtickets;

                }
                totaltickets.setText(numtickets+"");
            });
        }
    }
}