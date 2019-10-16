package com.bayex.bayex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bayex.bayex.Bayex.BindingSymptomHypothesis;
import com.bayex.bayex.Bayex.Symptom;
import java.util.ArrayList;

public class SymptomsDisplay extends AppCompatActivity {

    private ArrayList<Symptom> symptomes = new ArrayList<>();
    private BindingSymptomHypothesis BindingSymptomHypothesisTable = new BindingSymptomHypothesis();
    private int INDEX = 0;

    private TextView tvSymptomName;
    private TextView tvSymptomDescription;
    private TextView tvSymptomQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_display);

        symptomes = getIntent().getParcelableArrayListExtra("symptoms");
        BindingSymptomHypothesisTable = getIntent().getParcelableExtra("bindings");

        tvSymptomName = (TextView) findViewById(R.id.textSymptomName);
        tvSymptomQuestion = (TextView) findViewById(R.id.textQuestion);
        tvSymptomDescription = (TextView) findViewById(R.id.textDescription);

        tvSymptomName.setMovementMethod(new ScrollingMovementMethod());
        tvSymptomDescription.setMovementMethod(new ScrollingMovementMethod());
        tvSymptomQuestion.setMovementMethod(new ScrollingMovementMethod());

        final Button buttonNext = findViewById(R.id.button);
        final Button buttonPrev = findViewById(R.id.button2);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (INDEX < symptomes.size()) {
                    tvSymptomName.setText(String.valueOf(symptomes.get(INDEX).getSymptomName()));
                    tvSymptomDescription.setText(String.valueOf(symptomes.get(INDEX).getSymptomDescription()));
                    tvSymptomQuestion.setText(String.valueOf(symptomes.get(INDEX).getQuestion()));
                    validateIfPrevButtonIsEnabled();
                    INDEX++;
                }
                else
                {
                    validateIfPrevButtonIsEnabled();
                    buttonNext.setEnabled(false);
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No symptoms anymore!", Toast.LENGTH_SHORT).show();
                    INDEX--;
                }
            }

            private void validateIfPrevButtonIsEnabled(){
                if(!buttonPrev.isEnabled())
                {
                    buttonPrev.setEnabled(true);
                }
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (INDEX > -1) {
                    tvSymptomName.setText(String.valueOf(symptomes.get(INDEX).getSymptomName()));
                    tvSymptomDescription.setText(String.valueOf(symptomes.get(INDEX).getSymptomDescription()));
                    tvSymptomQuestion.setText(String.valueOf(symptomes.get(INDEX).getQuestion()));
                    validateIfNextButtonIsEnabled();
                    INDEX--;
                }
                else
                {
                    validateIfNextButtonIsEnabled();
                    buttonPrev.setEnabled(false);
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No symptoms anymore!", Toast.LENGTH_SHORT).show();
                    INDEX++;
                }
            }

            private void validateIfNextButtonIsEnabled(){
                if(!buttonNext.isEnabled())
                {
                    buttonNext.setEnabled(true);
                }
            }
        });
    }
}
