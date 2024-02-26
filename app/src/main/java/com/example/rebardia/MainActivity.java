package com.example.rebardia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2;
    private LinearLayout buttonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black_ash)));



        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityEightmm();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityTenmm();
            }
        });

    }

    public void openActivityEightmm(){
        Intent intent = new Intent(this, eightmm.class);
        startActivity(intent);
    }
    public void openActivityTenmm(){
        Intent intent = new Intent(this, tenmm.class);
        startActivity(intent);
    }
}