/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/16/19 10:04 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/16/19 10:04 PM
 *
 */

package com.example.hw1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class PlayerInputDialog extends AppCompatDialogFragment {
    private EditText editTextPlayername;
    private InputDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Please enter your name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String playerName = editTextPlayername.getText().toString();
                    }
                });

        editTextPlayername = view.findViewById(R.id.edit_playerName);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (InputDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement InputDialogListener");
        }
    }

    public interface InputDialogListener {
        void applyTexts(String playerName);
    }
}