package ru.obrubov.laba3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void action(View view) {
        Intent intent = new Intent(this, TextActivity.class);
        EditText editText = findViewById(R.id.editText);
        intent.putExtra("message", editText.getText().toString());
        startActivity(intent);
    }
}
