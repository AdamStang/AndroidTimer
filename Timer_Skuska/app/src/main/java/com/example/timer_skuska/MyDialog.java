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

public class MyDialog extends AppCompatDialogFragment {

    private NumberPicker nump;
    private NumberPicker nump2;
    private MyDialogListener listener;
    private String value1String;
    private String value2String;
    private int value1;
    private int value2;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settin, null);

        builder.setView(view);
        builder.setTitle("Choose time");

        nump = view.findViewById(R.id.numberPicker1);
        nump.setMinValue(0);
        nump.setMaxValue(59);
        //nump.setValue(1);
        nump2 = view.findViewById(R.id.numberPicker2);
        nump2.setFormatter(new NumberPicker.Formatter(){
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        nump2.setMinValue(00);
        nump2.setMaxValue(59);
        //nump2.setValue(00);

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                value1 = nump.getValue();
                value2 = nump2.getValue();
                value1String = String.valueOf(value1);
                value2String = String.valueOf(value2);
                if(value2String.length() < 2){
                    value2String = "0" + value2String;
                }
                String time = value1String + ":" + value2String;
                listener.applyTexts(time);
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
