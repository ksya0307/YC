package com.example.yc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class ErrorExist extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder existingUser = new AlertDialog.Builder(getActivity());
        existingUser.setTitle("Уведомление")
                .setMessage("Пользователь с таким логином уже существует, пожалуйста повторите попытку")
                .setPositiveButton("OK", (dialog, which) -> {

                });
        return existingUser.create();
    }


}
