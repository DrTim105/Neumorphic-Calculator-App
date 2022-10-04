package com.salihutimothy.neumorphiccalculatorapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import soup.neumorphism.NeumorphButton;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operation;

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NeumorphButton button0 = findViewById(R.id.btn_0);
        NeumorphButton button00 = findViewById(R.id.btn_00);
        NeumorphButton button1 = findViewById(R.id.btn_1);
        NeumorphButton button2 = findViewById(R.id.btn_2);
        NeumorphButton button3 = findViewById(R.id.btn_3);
        NeumorphButton button4 = findViewById(R.id.btn_4);
        NeumorphButton button5 = findViewById(R.id.btn_5);
        NeumorphButton button6 = findViewById(R.id.btn_6);
        NeumorphButton button7 = findViewById(R.id.btn_7);
        NeumorphButton button8 = findViewById(R.id.btn_8);
        NeumorphButton button9 = findViewById(R.id.btn_9);
        NeumorphButton buttonDot = findViewById(R.id.btn_dot);
        NeumorphButton buttonDivide = findViewById(R.id.btn_divide);
        NeumorphButton buttonMultiply = findViewById(R.id.btn_multiply);
        NeumorphButton buttonMinus = findViewById(R.id.btn_minus);
        NeumorphButton buttonPlus = findViewById(R.id.btn_plus);
        NeumorphButton buttonEquals = findViewById(R.id.btn_equals);
        NeumorphButton buttonNeg = findViewById(R.id.btn_neg);
        NeumorphButton buttonPercent = findViewById(R.id.btn_percent);
        NeumorphButton buttonAC = findViewById(R.id.btn_ac);
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        operation = findViewById(R.id.operation);

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final BigDecimalViewModel viewModel = ViewModelProviders.of(this).get(BigDecimalViewModel.class);

        viewModel.getStringResult().observe(this, stringResult -> {
            if (stringResult.equals("-1.0E+9")) {
                result.setText("Error");
            } else {
                result.setText(stringResult);
            }
        });
        viewModel.getStringNewNumber().observe(this, stringNumber -> newNumber.setText(stringNumber));
        viewModel.getStringOperation().observe(this, stringOperation -> operation.setText(stringOperation));

        View.OnTouchListener touchListener = (view, motionEvent) -> {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ((NeumorphButton) view).setShapeType(1);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPressed));
                    vibe.vibrate(2);
                }
                break;
                case MotionEvent.ACTION_UP: {
                    ((NeumorphButton) view).setShapeType(0);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
                    view.performClick();
                    viewModel.digitPressed(((NeumorphButton) view).getText().toString());
                }
                break;
            }
            return true;
        };

        button0.setOnTouchListener(touchListener);
        button00.setOnTouchListener(touchListener);
        button1.setOnTouchListener(touchListener);
        button2.setOnTouchListener(touchListener);
        button3.setOnTouchListener(touchListener);
        button4.setOnTouchListener(touchListener);
        button5.setOnTouchListener(touchListener);
        button6.setOnTouchListener(touchListener);
        button7.setOnTouchListener(touchListener);
        button8.setOnTouchListener(touchListener);
        button9.setOnTouchListener(touchListener);
        buttonDot.setOnTouchListener(touchListener);

        View.OnTouchListener operationListener = (view, motionEvent) -> {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ((NeumorphButton) view).setShapeType(1);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPressed));
                    vibe.vibrate(2);
                }
                break;
                case MotionEvent.ACTION_UP: {
                    ((NeumorphButton) view).setShapeType(0);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
                    view.performClick();
                    viewModel.operandPressed(((NeumorphButton) view).getText().toString());
                }
                break;
            }
            return true;
        };

        buttonEquals.setOnTouchListener(operationListener);
        buttonDivide.setOnTouchListener(operationListener);
        buttonMultiply.setOnTouchListener(operationListener);
        buttonMinus.setOnTouchListener(operationListener);
        buttonPlus.setOnTouchListener(operationListener);

        buttonAC.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ((NeumorphButton) view).setShapeType(1);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPressed));
                    vibe.vibrate(1);
                }
                break;
                case MotionEvent.ACTION_UP: {
                    ((NeumorphButton) view).setShapeType(0);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
                    viewModel.allClearPressed();
                    result.setText("");
                    view.performClick();
                }
                break;
            }
            return true;
        });

        buttonNeg.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ((NeumorphButton) view).setShapeType(1);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPressed));
                    vibe.vibrate(2);
                }
                break;
                case MotionEvent.ACTION_UP: {
                    ((NeumorphButton) view).setShapeType(0);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
                    viewModel.negPressed();
                    view.performClick();
                }
                break;
            }
            return true;
        });

        buttonPercent.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ((NeumorphButton) view).setShapeType(1);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColorPressed));
                    vibe.vibrate(2);
                }
                break;
                case MotionEvent.ACTION_UP: {
                    ((NeumorphButton) view).setShapeType(0);
                    ((NeumorphButton) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
                    viewModel.percentPressed();
                    view.performClick();
                }
                break;
            }
            return true;
        });
    }
}