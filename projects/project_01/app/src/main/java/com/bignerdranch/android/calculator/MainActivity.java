package com.bignerdranch.android.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private double total = 0;
    private String currentOperation = "";
    private boolean isNewInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int id : numberIds) {
            Button button = findViewById(id);
            button.setOnClickListener(view -> onNumberClick(button));
        }

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnEquals = findViewById(R.id.btnEquals);
        Button btnClear = findViewById(R.id.btnClear);

        btnAdd.setOnClickListener(view -> onOperationClick("+", btnAdd));
        btnSubtract.setOnClickListener(view -> onOperationClick("-", btnSubtract));

        btnEquals.setOnClickListener(view -> onEqualsClick());
        btnEquals.setOnLongClickListener(view -> {
            onClearClick();
            return true;
        });

        btnClear.setOnClickListener(view -> onClearClick());
    }

    private void onNumberClick(Button button) {
        String num = button.getText().toString();
        if (isNewInput) {
            display.setText(num);
        } else {
            display.append(num);
        }
        isNewInput = false;
    }

    private void onOperationClick(String operation, Button selectedButton) {
        if (!isNewInput) {
            total = calculate(total, Double.parseDouble(display.getText().toString()), currentOperation);
            display.setText(formatResult(total));
        }
        currentOperation = operation;
        isNewInput = true;

        resetOperationButtonColors();

        selectedButton.setBackgroundColor(getResources().getColor(R.color.buttonHighlighted));
    }

    private void resetOperationButtonColors() {
        findViewById(R.id.btnAdd).setBackgroundColor(getResources().getColor(R.color.buttonBackground));
        findViewById(R.id.btnSubtract).setBackgroundColor(getResources().getColor(R.color.buttonBackground));
    }

    private void onEqualsClick() {
        if (!currentOperation.isEmpty()) {
            total = calculate(total, Double.parseDouble(display.getText().toString()), currentOperation);
            display.setText(formatResult(total));
            currentOperation = "";
            isNewInput = true;
        }
    }

    private String formatResult(double result) {
        if (result == (int) result) {
            return String.valueOf((int) result);
        } else {
            return String.valueOf(result);
        }
    }

    private void onClearClick() {
        total = 0;
        currentOperation = "";
        display.setText(getString(R.string.default_display));
        Toast.makeText(this, getString(R.string.toast_cleared), Toast.LENGTH_SHORT).show();
    }

    private double calculate(double total, double newValue, String operation) {
        switch (operation) {
            case "+":
                return total + newValue;
            case "-":
                return total - newValue;
            default:
                return newValue;
        }
    }
}