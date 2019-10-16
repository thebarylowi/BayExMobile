package com.bayex.bayex;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bayex.bayex.Bayex.BindingSymptomHypothesis;
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.Bayex.Symptom;
import java.util.ArrayList;

public class ShowBaseActivity extends AppCompatActivity {
    private Button bHipotesis;
    private Button bSymptoms;
    private Button bMeta;
    private TextView hypoName;

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptomes = new ArrayList<>();
    private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
    private BindingSymptomHypothesis BindingSymptomHypothesisTable = new BindingSymptomHypothesis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_show_base);

        hypotheses = getIntent().getParcelableArrayListExtra("hypo");
        symptomes = getIntent().getParcelableArrayListExtra("symptoms");
        generalSymptoms = getIntent().getParcelableArrayListExtra("general");
        BindingSymptomHypothesisTable = getIntent().getParcelableExtra("bindings");

        bHipotesis = (Button) findViewById(R.id.bHipotesis);
        bSymptoms = (Button) findViewById(R.id.bSymptoms);
        bMeta = (Button) findViewById(R.id.bMeta);

        //final ConstraintLayout placeHolder = (ConstraintLayout) findViewById(R.id.hipLayout);
        final ConstraintLayout placeHolder2 = (ConstraintLayout) findViewById(R.id.symLayout);

        bHipotesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, HipothesisDisplay.class);
                i.putParcelableArrayListExtra("hypo", hypotheses);
                startActivity(i);

                //placeHolder.setVisibility(View.VISIBLE);
                placeHolder2.setVisibility(View.INVISIBLE);
            }
        });

        bSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, SymptomsDisplay.class);
                i.putParcelableArrayListExtra("symptoms", symptomes);
                i.putExtra("bindings", BindingSymptomHypothesisTable);
                startActivity(i);

                //placeHolder.setVisibility(View.INVISIBLE);
                placeHolder2.setVisibility(View.VISIBLE);

            }
        });

        bMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, MetawaysDisplay.class);
                i.putParcelableArrayListExtra("symptoms", symptomes);
                i.putParcelableArrayListExtra("general", generalSymptoms);
                startActivity(i);

              //  placeHolder.setVisibility(View.INVISIBLE);
                placeHolder2.setVisibility(View.INVISIBLE);
            }
        });
        //TODO: set hypo object to see content on screen

    }
}