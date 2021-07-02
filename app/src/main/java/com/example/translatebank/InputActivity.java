package com.example.translatebank;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.translatebank.Repository.TranslationDTO;
import com.example.translatebank.Repository.Translations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InputActivity extends AppCompatActivity {

    private Button recordVoiceBtn;
    private TextView inputTextView;
    private TextView translatedTextView;
    private Button resetBtn;
    private Button saveBtn;
    private Translations db;
    private String targetLanguage = "fr";
    private String inputLanguage = "en";
    private Boolean hasInput = false;

    private String key = "efeb6af7e9004dc9bc66fa69f18784bb";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        inputTextView = findViewById(R.id.textView);
        translatedTextView = findViewById(R.id.textView2);
        inputTextView.setText("");
        translatedTextView.setText("");

        db = new Translations(this);

        recordVoiceBtn = findViewById(R.id.recordButton);
        recordVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer();
            }
        });

        resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTextView.setText("");
                translatedTextView.setText("");
            }
        });

        saveBtn = findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!hasInput){
                    return;
                }

                TranslationDTO dto = new TranslationDTO();
                dto.setTo(targetLanguage);
                dto.setFrom(inputLanguage);
                dto.setDatetime(String.valueOf(System.currentTimeMillis()));
                dto.setResult(translatedTextView.getText().toString());
                dto.setOriginal(inputTextView.getText().toString());
                db.add(dto);
                inputTextView.setText("");
                translatedTextView.setText("");
                hasInput = false;
                db.close();
            }
        });
    }

    private static final int SPEECH_REQUEST_CODE = 0;
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            inputTextView.setText(spokenText);

            try {
                callTranslationAPI(spokenText);
            } catch (Exception e) {
                return;
            }

            hasInput = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void callTranslationAPI(String text) throws Exception {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,"https://juliuskindlundhabc.cognitiveservices.azure.com//translator/text/v3.0/translate?to="+targetLanguage
                ,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 try {
                     String str = ((JSONObject)((JSONArray)(((JSONObject)response.getJSONObject(0)).get("translations"))).get(0)).get("text").toString();
                     setText(str);
                 }
                 catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setText("");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Ocp-Apim-Subscription-Key", "efeb6af7e9004dc9bc66fa69f18784bb");
                headers.put("Content-Type", "application/json");
                headers.put("Ocp-Apim-Subscription-Region","northeurope");
                headers.put("Content-Length",String.valueOf(text.length()+13));
                return headers;
            }
            @Override
            public byte[] getBody(){
                return ("[{'Text':'"+text+"'}]").getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        try{
            request.getHeaders();
            request.getBody();
            request.getBodyContentType();
            Log.d("POST",request.getHeaders().toString());
            Log.d("POST",new String(request.getBody()));

        }
        catch(Exception e){}

        queue.add(request);
    }

    private void setText(String result) {
        translatedTextView.setText(result);
    }
}
