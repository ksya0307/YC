package com.example.yc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SuccessBuy extends AppCompatDialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder successPurchase = new AlertDialog.Builder(getActivity());
        successPurchase.setTitle("Уведомление")
                .setMessage("ВЫ ПРИОБРЕЛИ БИЛЕТ(-ы)!\nСпасибо, что выбрали нас!\nПриятного просмотра!")
                .setPositiveButton("ОК", (dialog, which) -> {
                });
        return  successPurchase.create();
    }
}
