package com.bayex.bayex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bayex.bayex.Bayex.BindingSymptomHypothesis;
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.Bayex.KnowledgeBase;
import com.bayex.bayex.Bayex.Symptom;
import com.bayex.bayex.Utils.Files;

import java.util.ArrayList;

public class    BaseSelectedActivity extends AppCompatActivity {
    private Button bStartAnalyze;
    private Button bShowBase;
    private TextView tvBaseVersion;
    private TextView tvBaseNumber;
    private TextView tvBaseInfo;

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptomes = new ArrayList<>();
    private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
    private BindingSymptomHypothesis BindingSymptomHypothesisTable = new BindingSymptomHypothesis();

    public Files files;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_selected);

        hypotheses = getIntent().getExtras().getParcelableArrayList("hypo");
        symptomes = getIntent().getParcelableArrayListExtra("symptoms");
        generalSymptoms = getIntent().getParcelableArrayListExtra("general");
        BindingSymptomHypothesisTable = getIntent().getParcelableExtra("bindings");

        files = new Files();
        bShowBase = (Button) findViewById(R.id.bshowBase);
        bStartAnalyze = (Button) findViewById(R.id.bstartAnalise);

        tvBaseNumber = (TextView) findViewById(R.id.tvBaseNumberSet);
        tvBaseVersion = (TextView) findViewById(R.id.tvBaseVersionSet);
        tvBaseInfo = (TextView) findViewById(R.id.tvBaseInfoSet);
        tvBaseInfo.setMovementMethod(new ScrollingMovementMethod());

        bShowBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, ShowBaseActivity.class);
                i.putParcelableArrayListExtra("hypo", hypotheses);
                i.putParcelableArrayListExtra("symptoms",symptomes);
                i.putParcelableArrayListExtra("general",generalSymptoms);
                i.putExtra("bindings",BindingSymptomHypothesisTable);
                startActivity(i);
            }
        });

        bStartAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, Expertise_display.class);
                i.putParcelableArrayListExtra("hypo", hypotheses);
                i.putParcelableArrayListExtra("symptoms",symptomes);
                i.putParcelableArrayListExtra("general",generalSymptoms);
                i.putExtra("bindings",BindingSymptomHypothesisTable);
                startActivity(i);
            }
        });

        tvBaseVersion.setText(String.valueOf(KnowledgeBase.getBaseVersion()));
        tvBaseNumber.setText(String.valueOf(KnowledgeBase.getBaseNumber()));
        tvBaseInfo.setText(KnowledgeBase.getDescription());
    }
}
