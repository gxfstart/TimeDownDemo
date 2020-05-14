package com.gxf.timedowndemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gxf.timedownview.TimeDownView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TimeDownView timeDownView = findViewById(R.id.timeDownView);
        timeDownView.setTimeDownCallBack(new TimeDownView.TimeDownCallBack() {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "已完成", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDownView.startCountDownTimer(10 * 1000);
            }
        });
    }
}
