package com.example.timer_skuska;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MyDialog2 extends AppCompatDialogFragment {

    private NumberPicker nump3;
    private MyDialogListener listener;
    private String rounds;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.my_layout, null);

        builder.setView(view);
        builder.setTitle("Choose rounds");

        nump3 = view.findViewById(R.id.numberPicker3);
        nump3.setMinValue(0);
        nump3.setMaxValue(99);

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rounds = "" + nump3.getValue();
                listener.applyTexts(rounds);
            }
        });

        return builder.create();
    }

    public interface MyDialogListener{
        void applyTexts(String time);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = (MyDialogListener) context;
    }
}
