package com.salihutimothy.neumorphiccalculatorapp;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

import kotlin.jvm.internal.Intrinsics;


public class BigDecimalViewModel extends ViewModel {

    private BigDecimal operand1;
    private String pendingOperation = "=";
    private final MutableLiveData<BigDecimal> result = new MutableLiveData<>();
    private final MutableLiveData<String> newNumber = new MutableLiveData<>();
    private final MutableLiveData<String> operation = new MutableLiveData<>();
//    private final MutableLiveData<String> op;

//    public BigDecimalViewModel(MutableLiveData<String> op) {
//        this.op = op;
//    }


    @NotNull
    public final LiveData<String> getStringResult() {
        LiveData<String> stringResult = Transformations.map((LiveData<BigDecimal>)this.result, BigDecimal::toString);
        Intrinsics.checkNotNullExpressionValue(stringResult, "Transformations.map(result) { it.toString()}");
        return stringResult;
    }

    @NotNull
    public final LiveData<String> getStringNewNumber() {
        return (LiveData<String>)this.newNumber;
    }

    @NotNull
    public final LiveData<String> getStringOperation() {
        return (LiveData<String>)this.operation;
    }

    public final void digitPressed(@NotNull String caption) {
        Intrinsics.checkNotNullParameter(caption, "caption");
        if (this.newNumber.getValue() != null) {
            this.newNumber.setValue(Intrinsics.stringPlus((String)this.newNumber.getValue(), caption));
        } else {
            this.newNumber.setValue(caption);
        }

    }

    public final void operandPressed(@NotNull String op) {

        Intrinsics.checkNotNullParameter(op, "op");

        try {
            String val = (String)this.newNumber.getValue();
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
        String value = (String)this.newNumber.getValue();
        if (value != null) {
            if (((CharSequence)value).length() != 0) {
                try {
                    BigDecimal doubleValue = new BigDecimal(value);
                    BigDecimal negVal = BigDecimal.valueOf(-1L);
                    Intrinsics.checkNotNullExpressionValue(negVal, "BigDecimal.valueOf(-1)");
//                    BigDecimal var4 = var10000;
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

    public final void allClearPressed() {
        this.newNumber.setValue("") ;
        this.operation.setValue("");
        this.operand1 = null;
        this.result.setValue(BigDecimal.ZERO);
    }

    private void performOperation(BigDecimal value, String operation) {
        if (this.operand1 == null) {
            this.operand1 = value;
        } else {
//            if (Intrinsics.areEqual(this.pendingOperation, "=")) {
                if (this.pendingOperation.equals("=")) {
                this.pendingOperation = operation;
            }

            String pendOp = this.pendingOperation;
            BigDecimal temp;
            BigDecimal var4;
            switch(pendOp.hashCode()) {
                case 42:
                    if (pendOp.equals("*")) {
                        temp = this.operand1;
                        Intrinsics.checkNotNull(temp);
//                        var4 = temp;
                        temp = temp.multiply(value);
                        Intrinsics.checkExpressionValueIsNotNull(temp, "this.multiply(other)");
                        this.operand1 = temp;
                    }
                    break;
                case 43:
                    if (pendOp.equals("+")) {
                        temp = this.operand1;
                        Intrinsics.checkNotNull(temp);
//                        var4 = temp;
                        temp = temp.add(value);
                        Intrinsics.checkExpressionValueIsNotNull(temp, "this.add(other)");
                        this.operand1 = temp;
                    }
                    break;
                case 45:
                    if (pendOp.equals("-")) {
                        temp = this.operand1;
                        Intrinsics.checkNotNull(temp);
//                        var4 = temp;
                        temp = temp.subtract(value);
                        Intrinsics.checkExpressionValueIsNotNull(temp, "this.subtract(other)");
                        this.operand1 = temp;
                    }
                    break;
                case 47:
                    if (pendOp.equals("/")) {
                        if (Intrinsics.areEqual(value, BigDecimal.valueOf(0.0D))) {
                            temp = BigDecimal.valueOf(Double.NaN);
                        } else {
                            Double dividend = this.operand1.doubleValue();
                            Double divisor = value.doubleValue();
                            Intrinsics.checkNotNull(dividend);
                            Intrinsics.checkNotNull(divisor);
//                            var4 = temp;
                            temp = BigDecimal.valueOf(dividend/divisor);
//                                    temp.divide(value, 0, RoundingMode.HALF_EVEN);
                            Intrinsics.checkExpressionValueIsNotNull(temp, "BigDecimal.valueOf(dividend/divisor)");
                        }

                        this.operand1 = temp;
                    }
                    break;
                case 61:
                    if (pendOp.equals("=")) {
                        this.operand1 = value;
                    }
            }
        }

        this.result.setValue(this.operand1);
        this.newNumber.setValue("");
    }


}
