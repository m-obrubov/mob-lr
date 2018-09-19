package ru.obrubov.laba3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        String message = getIntent().getStringExtra("message");
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
}
