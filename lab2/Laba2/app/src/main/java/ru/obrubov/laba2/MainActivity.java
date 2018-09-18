package ru.obrubov.laba2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private StringBuilder inputText;
    private double number;
    private char operation;

    private void updateTextView() {
        TextView textView = findViewById(R.id.textView);
        textView.setText(this.inputText.toString());
    }

    private void setInputText(CharSequence number) {
        inputText.append(number);
        updateTextView();
    }

    private void setOperation(CharSequence operation) {
        this.number = Double.valueOf(this.inputText.toString());
        this.operation = operation.charAt(0);
        this.inputText = new StringBuilder();
        updateTextView();
    }

    private void deleteNumber() {
        this.inputText.deleteCharAt(this.inputText.length() - 1);
        updateTextView();
    }

    private void calculate() {
        double secondNumber = Double.valueOf(this.inputText.toString());
        double result = 0;
        switch (this.operation) {
            case '*':
                result = this.number * secondNumber;
                break;
            case '/':
                if(secondNumber != 0) {
                    result = this.number / secondNumber;
                }
                break;
            case '+':
                result = this.number + secondNumber;
                break;
            case '-':
                result = this.number - secondNumber;
                break;
        }
        TextView textView = findViewById(R.id.textView);
        textView.setText(String.valueOf(result));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inputText = new StringBuilder();
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        setInputText(button.getText());
    }

    public void onOperation(View view) {
        Button button = (Button) view;
        setOperation(button.getText());
    }

    public void onEqual(View view) {
        calculate();
    }

    public void onDelete(View view) {
        deleteNumber();
    }
}
