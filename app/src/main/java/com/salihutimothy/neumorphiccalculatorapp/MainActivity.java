package com.salihutimothy.neumorphiccalculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity {

    private Button button0;
    private Button button00;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonDot;
    private Button buttonDivide;
    private Button buttonMultiply;
    private Button buttonMinus;
    private Button buttonPlus;
    private Button buttonEquals;
    private Button buttonNeg;
    private Button buttonC;
    private Button buttonAC;
    private EditText result;
    private EditText newNumber;
    private TextView operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}