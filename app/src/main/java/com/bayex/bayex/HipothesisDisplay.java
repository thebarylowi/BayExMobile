package com.bayex.bayex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bayex.bayex.Bayex.Hypothesis;
import java.util.ArrayList;


public class HipothesisDisplay extends AppCompatActivity {

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private int INDEX = 0;

    private TextView tvHypothesisName;
    private TextView tvHypothesisDescription;
    private TextView tvHypothesisPiority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hipothesis_display);

        hypotheses = getIntent().getParcelableArrayListExtra("hypo");

        tvHypothesisName = (TextView) findViewById(R.id.texthipothesisName);
        tvHypothesisDescription = (TextView) findViewById(R.id.textHypoDesc);
        tvHypothesisPiority = (TextView) findViewById(R.id.textPiority);

        tvHypothesisName.setMovementMethod(new ScrollingMovementMethod());
        tvHypothesisDescription.setMovementMethod(new ScrollingMovementMethod());
        tvHypothesisPiority.setMovementMethod(new ScrollingMovementMethod());


        final Button buttonNext = findViewById(R.id.buttonNext);
        final Button buttonPrev = findViewById(R.id.buttonPrev);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (INDEX < hypotheses.size()) {
                    tvHypothesisName.setText(String.valueOf(hypotheses.get(INDEX).getHypothesisName()));
                    tvHypothesisDescription.setText(String.valueOf(hypotheses.get(INDEX).getHypothesisDescription()));
                    tvHypothesisPiority.setText(String.valueOf(hypotheses.get(INDEX).getHypothseisProbability()));
                    validateIfPrevButtonIsEnabled();
                    INDEX++;
                }
                else
                {
                    validateIfPrevButtonIsEnabled();
                    buttonNext.setEnabled(false);
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No hypothesis anymore!", Toast.LENGTH_SHORT).show();
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
                    tvHypothesisName.setText(String.valueOf(hypotheses.get(INDEX).getHypothesisName()));
                    tvHypothesisDescription.setText(String.valueOf(hypotheses.get(INDEX).getHypothesisDescription()));
                    tvHypothesisPiority.setText(String.valueOf(hypotheses.get(INDEX).getHypothseisProbability()));
                    validateIfNextButtonIsEnabled();
                    INDEX--;
                }
                else
                {
                    validateIfNextButtonIsEnabled();
                    buttonPrev.setEnabled(false);
                    Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No hypothesis anymore!", Toast.LENGTH_SHORT).show();
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
