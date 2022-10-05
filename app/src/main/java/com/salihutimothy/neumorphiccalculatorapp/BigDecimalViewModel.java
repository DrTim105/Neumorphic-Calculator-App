package com.salihutimothy.neumorphiccalculatorapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

import kotlin.jvm.internal.Intrinsics;


public class BigDecimalViewModel extends ViewModel {

    private final MutableLiveData<BigDecimal> result = new MutableLiveData<>();
    private final MutableLiveData<String> newNumber = new MutableLiveData<>();
    private final MutableLiveData<String> operation = new MutableLiveData<>();
    private BigDecimal operand1;
    private String pendingOperation = "=";

    @NotNull
    public final LiveData<String> getStringResult() {
        LiveData<String> stringResult = Transformations.map(this.result, BigDecimal::toString);
        Intrinsics.checkNotNullExpressionValue(stringResult, "Transformations.map(result) { it.toString()}");
        return stringResult;
    }

    @NotNull
    public final LiveData<String> getStringNewNumber() {
        return this.newNumber;
    }

    @NotNull
    public final LiveData<String> getStringOperation() {
        return this.operation;
    }

    public final void digitPressed(@NotNull String caption) {
        Intrinsics.checkNotNullParameter(caption, "caption");
        if (this.newNumber.getValue() != null) {
            this.newNumber.setValue(Intrinsics.stringPlus(this.newNumber.getValue(), caption));
        } else {
            this.newNumber.setValue(caption);
        }
    }

    public final void operandPressed(@NotNull String op) {
        Intrinsics.checkNotNullParameter(op, "op");

        try {
            String val = this.newNumber.getValue();
            Log.d("test", "operand: " + op + "value: " + val);

            BigDecimal value;
            if (val != null) {
                value = new BigDecimal(val);
            } else {
                value = null;
            }

            if (value != null) {
                this.performOperation(value, op);
            }
        } catch (NumberFormatException e) {
            this.newNumber.setValue("");
        }

        this.pendingOperation = op;
        this.operation.setValue(this.pendingOperation);
    }

    public final void negPressed() {
        String value = this.newNumber.getValue();
        if (value != null) {
            if (((CharSequence) value).length() != 0) {
                try {
                    BigDecimal doubleValue = new BigDecimal(value);
                    BigDecimal negVal = BigDecimal.valueOf(-1L);
                    Intrinsics.checkNotNullExpressionValue(negVal, "BigDecimal.valueOf(-1)");
                    BigDecimal val = doubleValue.multiply(negVal);
                    Intrinsics.checkExpressionValueIsNotNull(val, "this.multiply(other)");
                    doubleValue = val;
                    this.newNumber.setValue(doubleValue.toString());
                } catch (NumberFormatException e) {
                    this.newNumber.setValue("");
                }
                return;
            }
        }
        this.newNumber.setValue("-");
    }

    public final void percentPressed() {
        String value = this.newNumber.getValue();
        BigDecimal temp;

        if (value == null) {
            this.operandPressed("%");
        } else {
            if (!value.equals("") & !value.equals("-") & operand1 == null) {
                BigDecimal val = new BigDecimal(value);
                double dividend = val.doubleValue();
                Intrinsics.checkNotNull(dividend);
                double quotient = dividend / 100;
                temp = BigDecimal.valueOf(quotient);
                this.operand1 = temp;
                this.result.setValue(this.operand1);
                this.newNumber.setValue("");
            } else {
                this.operandPressed("%");
            }
        }
    }

    public final void allClearPressed() {
        this.newNumber.setValue("");
        this.operation.setValue("");
        this.pendingOperation = "";
        operand1 = null;
        this.result.setValue(BigDecimal.ZERO);
    }

    private void performOperation(BigDecimal value, String operation) {
        Log.d("test", "operand: " + operation + "value: " + value);
        if (this.operand1 == null) {
            this.operand1 = value;
        } else {
            if (this.pendingOperation.equals("=")) {
                this.pendingOperation = operation;
            }

            if (Intrinsics.areEqual(this.operand1, BigDecimal.valueOf(-1.0E+9))) {
                this.operand1 = BigDecimal.valueOf(0);
            }

            String pendOp = this.pendingOperation;

            BigDecimal temp;
            switch (pendOp) {
                case "x":
                    temp = this.operand1;
                    Intrinsics.checkNotNull(temp);
                    temp = temp.multiply(value);
                    Intrinsics.checkExpressionValueIsNotNull(temp, "this.multiply(other)");
                    this.operand1 = temp;
                    break;
                case "+":
                    temp = this.operand1;
                    Intrinsics.checkNotNull(temp);
                    temp = temp.add(value);
                    Intrinsics.checkExpressionValueIsNotNull(temp, "this.add(other)");
                    this.operand1 = temp;
                    break;
                case "-":
                    temp = this.operand1;
                    Intrinsics.checkNotNull(temp);
                    temp = temp.subtract(value);
                    Intrinsics.checkExpressionValueIsNotNull(temp, "this.subtract(other)");
                    this.operand1 = temp;
                    break;
                case "%":
                    temp = this.operand1;
                    double val = temp.doubleValue();
                    Intrinsics.checkNotNull(val);
                    double percent = val / 100;
                    temp = BigDecimal.valueOf(percent);
                    temp = temp.multiply(value);
                    Intrinsics.checkExpressionValueIsNotNull(temp, "this.multiply(other)");
                    this.operand1 = temp;
                    break;
                case "÷":
                    if (Intrinsics.areEqual(value, BigDecimal.valueOf(0))) {
                        temp = BigDecimal.valueOf(-1.0E+9); // value to represent error
                        allClearPressed();
                    } else {
                        Double dividend = this.operand1.doubleValue();
                        Double divisor = value.doubleValue();
                        Intrinsics.checkNotNull(dividend);
                        Intrinsics.checkNotNull(divisor);
                        double quotient = dividend / divisor;
                        if ((int) quotient == quotient) {
                            temp = BigDecimal.valueOf((int) quotient);
                        } else {
                            temp = BigDecimal.valueOf(quotient);
                        }
                        Intrinsics.checkExpressionValueIsNotNull(temp, "BigDecimal.valueOf(dividend/divisor)");
                    }
                    this.operand1 = temp;
                    break;
                case "=":
                    this.operand1 = value;
            }
        }

        this.result.setValue(this.operand1);
        this.newNumber.setValue("");
    }

}


