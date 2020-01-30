package com.shzhangji.invest;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MessageBox extends DialogFragment {
    private String message;

    public MessageBox(String message) {
        this.message = message;
    }

    @Override @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", (dialog, id) -> {})
                .create();
    }
}
