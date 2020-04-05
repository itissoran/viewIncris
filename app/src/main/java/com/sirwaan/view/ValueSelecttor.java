package com.sirwaan.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ValueSelecttor extends LinearLayout implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

   private android.os.Handler handler;
    View rootView;
    TextView valueTextNumber;
    View buttonPlus;
    View buttunMine;
    int minVal = Integer.MIN_VALUE;
    int maxVal = Integer.MAX_VALUE;
    private boolean isPlusButtonPress = false;
    private boolean isMinunButtonPress = false;
    private static final int TIME_INTERVAL = 200; //ms

    public ValueSelecttor(Context context) {
        super(context);
        init(context);
    }

    public ValueSelecttor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }


    private void init(Context context) {
        rootView = inflate(context, R.layout.value_selector, this);

        valueTextNumber = rootView.findViewById(R.id.next_value_number);
        buttonPlus = rootView.findViewById(R.id.image_view_plus);
        buttunMine = rootView.findViewById(R.id.image_view_menha);



        buttonPlus.setOnClickListener(this);
        buttunMine.setOnClickListener(this);


        buttonPlus.setOnLongClickListener(this);
        buttunMine.setOnLongClickListener(this);
        buttonPlus.setOnTouchListener(this);
        buttunMine.setOnTouchListener(this);

        //import handler os
        handler=new Handler();
    }


    public int getValue() {
        String text = valueTextNumber.getText().toString();
        if (text.isEmpty()) {
            valueTextNumber.setText("0");
            return 0;
        }
        return Integer.valueOf(text);
    }

    public void setValue(int newValue) {
        if (newValue > maxVal) {
            valueTextNumber.setText(String.valueOf(maxVal));

        } else if (newValue < minVal) {
            valueTextNumber.setText(String.valueOf(minVal));
        } else {
            valueTextNumber.setText(String.valueOf(newValue));
        }
    }

    private void decreamentValue() {
        int value = getValue();

        setValue(value - 1);


    }

    private void increamentValue() {
        int value = getValue();
        setValue(value + 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == buttunMine.getId()) {
            decreamentValue();
        } else if (id == buttonPlus.getId()) {
            increamentValue();

        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //تشخیص میدیم که آیا دستش روی اون دکمه نگه داشته یا نه
        if (event.getAction() == event.ACTION_UP || event.getAction() == event.ACTION_CANCEL) {
            //  if (view.getId()==buttonPlus.getId()){

            isPlusButtonPress = false;
            //        }

            //    else if (view.getId()==buttunMine.getId()){
            isMinunButtonPress = false;
            //      }


        }

        return false;
    }

    @Override
    public boolean onLongClick(View v) {

        int id = v.getId();

        if (id == buttonPlus.getId()) {
            isPlusButtonPress = true;
            handler.postDelayed(new AutoIncrementer(),TIME_INTERVAL);


        } else if (id == buttunMine.getId()) {
            isMinunButtonPress = true;
            handler.postDelayed(new AutoDerementer(),TIME_INTERVAL);


        }
        return false;
    }

    private class AutoDerementer implements Runnable {
        @Override
        public void run() {
            if (isMinunButtonPress) {

                decreamentValue();

                handler.postDelayed(this,TIME_INTERVAL); //باید همین فعالیت رو تکرار کنی با این دستور هر 200 میلی ثانیه
                // یه بار (یعنی اگه کاربر روی این دکمه لانگ کلیک کرده
                // و هنوزم دستش روش نگه داشته این کد هر 200 میلی ثانیه بررسی می کند
                // اگه هنوزم دستش روش نگه داشته مقرارشو افزایش میده)

            }

        }
    }

    private class AutoIncrementer implements Runnable {
        @Override
        public void run() {
            if (isPlusButtonPress) {

                increamentValue();
                handler.postDelayed(this,TIME_INTERVAL);
            }


        }
    }
}
