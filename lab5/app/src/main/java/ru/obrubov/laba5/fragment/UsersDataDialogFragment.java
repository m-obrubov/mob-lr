package ru.obrubov.laba5.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class UsersDataDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        String text;
        text = getArguments() != null ? (String) getArguments().get("text") : "Ошибка";
        return builder
                .setTitle("Пользователи")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage(text)
                .setPositiveButton("OK", null)
                .create();
    }
}
