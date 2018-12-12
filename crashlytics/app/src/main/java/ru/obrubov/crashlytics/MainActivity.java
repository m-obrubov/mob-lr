package ru.obrubov.crashlytics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private Button mCrashNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        mCrashNowButton = findViewById(R.id.btn_crash_now);
        mCrashNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crashNow();
            }
        });
    }

    private void crashNow() {
        Crashlytics.log("This is a crash!");
        throw new RuntimeException("This is a crash");
    }
}
