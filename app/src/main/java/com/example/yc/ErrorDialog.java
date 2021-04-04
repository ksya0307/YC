package com.example.yc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ErrorDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder existingUser = new AlertDialog.Builder(getActivity());
        existingUser.setTitle("Уведомление")
                .setMessage("Пользователь с таким логином уже существует, пожалуйста повторите попытку")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return existingUser.create();
    }
}
