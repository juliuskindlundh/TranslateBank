package com.example.translatebank;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button toTranslate;
    private Button toSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiate navigation buttons
        toTranslate = findViewById(R.id.toTranslateBtn);
        toSaved = findViewById(R.id.toSavedBtn);
        toTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo(InputActivity.class);
            }
        });
        toSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTo(ViewSavedTranslations.class);
            }
        });
    }

    private void switchTo(Class activity) {
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }


}