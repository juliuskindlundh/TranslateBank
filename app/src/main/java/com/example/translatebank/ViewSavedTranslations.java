package com.example.translatebank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.translatebank.Repository.TranslationDTO;
import com.example.translatebank.Repository.Translations;

import java.util.ArrayList;

public class ViewSavedTranslations extends AppCompatActivity {

    private RecyclerView translationView;
    private TranslationAdapter translationAdapter;
    private Translations db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsavedtranslations);

        db = new Translations(this);

        ArrayList<TranslationDTO> translationDTOs = db.getAll();
        db.close();

        translationView = findViewById(R.id.translationView);

        translationView.setLayoutManager( new LinearLayoutManager(this));

        translationAdapter = new TranslationAdapter(translationDTOs);

        translationView.setAdapter(translationAdapter);
    }
}
