package com.bayex.bayex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.Symptom;
import java.util.ArrayList;

public class MetawaysDisplay extends AppCompatActivity {
    private ArrayList<Symptom> symptomes = new ArrayList<>();
    private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
    private int INDEX = 0;

    private TextView tvGeneralSymptomName;
    private TextView tvGeneralSymptomDescription;
    private TextView tvSymptomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metaways_display);

        symptomes = getIntent().getParcelableArrayListExtra("symptoms");
        generalSymptoms = getIntent().getParcelableArrayListExtra("general");

        tvGeneralSymptomName = (TextView) findViewById(R.id.textGeneralSymptomName);
        tvGeneralSymptomDescription = (TextView) findViewById(R.id.textGeneralSymptomDescription);
        tvSymptomList = (TextView) findViewById(R.id.textSymptomesList);

        tvGeneralSymptomName.setMovementMethod(new ScrollingMovementMethod());
        tvGeneralSymptomDescription.setMovementMethod(new ScrollingMovementMethod());
        tvSymptomList.setMovementMethod(new ScrollingMovementMethod());

        final Button buttonNext = findViewById(R.id.button4);
        final Button buttonPrev = findViewById(R.id.button3);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                tvSymptomList.setText(displaySymptomesList());
                if (INDEX < generalSymptoms.size()) {
                    tvGeneralSymptomName.setText(String.valueOf(generalSymptoms.get(INDEX).getGeneralSymptomName()));
                    tvGeneralSymptomDescription.setText(String.valueOf(generalSymptoms.get(INDEX).getGeneralSymptomDescription()));
                    INDEX++;
                    validateIfPrevButtonIsEnabled();
                }
                else
                {
                    INDEX--;
                    buttonNext.setEnabled(false);
                    validateIfPrevButtonIsEnabled();
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No metaways anymore!", Toast.LENGTH_SHORT).show();
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
                tvSymptomList.setText(displaySymptomesList());
                if (INDEX > -1) {
                    tvGeneralSymptomName.setText(String.valueOf(generalSymptoms.get(INDEX).getGeneralSymptomName()));
                    tvGeneralSymptomDescription.setText(String.valueOf(generalSymptoms.get(INDEX).getGeneralSymptomDescription()));
                    validateIfNextButtonIsEnabled();
                    INDEX--;
                }
                else
                {
                    validateIfNextButtonIsEnabled();
                    buttonPrev.setEnabled(false);
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No metaways anymore!", Toast.LENGTH_SHORT).show();
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

    private String displaySymptomesList()
    {
        int symptomeListSize = symptomes.size();
        String symptomList = "";
        for (int i = 0; i < symptomeListSize; i++) {
            symptomList += String.valueOf(symptomes.get(i).getSymptomName()) + "\n";
        }
        return symptomList;
    }
}
