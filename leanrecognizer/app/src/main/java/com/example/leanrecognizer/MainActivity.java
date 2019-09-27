package com.example.leanrecognizer;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private TextView voice;
    private Button btn_lancar_resposta;
    private RxPermissions mPermissions;
    private TextView respostas_corretas;
    private TextView respostas_alunos;
    String respostas = "ABCDABABDCEDFAEFDCACAD";
    String respostas_aluno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voice = findViewById(R.id.textView_text_to_voice_id);
        btn_lancar_resposta = findViewById(R.id.button_lancar_resposta_id);
        respostas_alunos = findViewById(R.id.textView_valida_resposta_aluno_id);
        respostas_corretas = findViewById(R.id.textView_valida_resposta_correta_id);

        btn_lancar_resposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startVoiceInput();
            }
        });



    }

    private RxPermissions getRxPermissions() {
        if (mPermissions == null) {
            mPermissions = new RxPermissions(this);
        }
        return mPermissions;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        // intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, new long[50000]);
        //intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, new long[50000]);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fala ai truta, estou te ouvindo");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voice.setText(result.get(0));
                    respostas_alunos.setText(result.get(0).toUpperCase());
                    respostas_corretas.setText(respostas);

                    /*
                    if(respostas_aluno.indexOf(0) == respostas.indexOf(0)){
                        respostas_corretas.setBackgroundResource(R.color.colorGreen);
                        respostas_corretas.setText(respostas.indexOf(0));
                    }else{
                        respostas_corretas.setBackgroundResource(R.color.colorRed);
                        respostas_corretas.setText(respostas.indexOf(0));
                    }
                    */


                }
                break;
            }
        }
    }

    private boolean isNumber(ArrayList<String> word)
    {
        boolean isNumber = false;
        try
        {
            Integer.parseInt(String.valueOf(word));
            isNumber = true;
        } catch (NumberFormatException e)
        {
            isNumber = false;
        }
        return isNumber;
    }

}
