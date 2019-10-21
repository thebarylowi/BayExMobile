package com.bayex.bayex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bayex.bayex.Bayex.Answers;
import com.bayex.bayex.Bayex.BindingSymptomHypothesis;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.Bayex.Symptom;
import com.bayex.bayex.Bayex.Test;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Expertise_display extends AppCompatActivity {

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptoms = new ArrayList<>();
    private BindingSymptomHypothesis BindingSymptomHypothesisTable = new BindingSymptomHypothesis();
    private Test test = new Test();
    private ArrayList<Answers> answers = new ArrayList<>();

    private Button bYea;
    private Button bNo;
    private Button bMaybeYea;
    private Button bMaybeNot;
    private Button bDontKnow;
    private Button bShowReult;
    private Button bShowAnswers;

    private int generalQuestIndex = 0;
    private int symptomQuestIndex = 0;
    private int generalIndex = 0;
    private int index = 0;

    private TextView bQuest;

    private void addAnswer(String p_question, String p_name, int p_answer)
    {
        Answers temp = new Answers(p_question, p_name, p_answer);
        answers.add(temp);
        generalIndex++;
    }

    private void displaySymptomQuestion(String p_questionToDisplay)
    {
        bMaybeYea.setEnabled(true);
        bMaybeNot.setEnabled(true);
        bQuest.setText(String.valueOf(p_questionToDisplay));
    }

    private boolean validateIfAnySymptomsQuestionsLeft()
    {
        return symptomQuestIndex < symptoms.size();
    }

    private String computeDiagnosticValue()
    {
        ArrayList<BigDecimal> diagnosticValues = new ArrayList<>();
        BigDecimal max = new BigDecimal("-1.0");
        String l_mostAccurateQuestion = "";

        for(int i = 0; i < BindingSymptomHypothesisTable.getSymptomNames().size(); i++)
        {
            diagnosticValues.add(new BigDecimal("0.0"));
        }

        BigDecimal result = new BigDecimal("0.0");
        BigDecimal py = new BigDecimal("0.0");
        BigDecimal pn = new BigDecimal("0.0");
        BigDecimal one = new BigDecimal("1.0");
        BigDecimal zero = new BigDecimal("0.0");
        BigDecimal pe;

        for(int i = 0; i < hypotheses.size(); i++)
        {
            BigDecimal p = new BigDecimal(hypotheses.get(i).getHypothseisProbability());
            for(int j = 0; j < BindingSymptomHypothesisTable.getSymptomNames().size(); j++) {
                if (symptoms.size() != 0 && symptoms.get(j).isAnswered() == false) {

                    for (int k = 0; k < BindingSymptomHypothesisTable.get_p1_list().get(j).size(); k++) {
                        py = new BigDecimal(BindingSymptomHypothesisTable.get_p1_list().get(j).get(k));
                        pn = new BigDecimal(BindingSymptomHypothesisTable.get_p2_list().get(j).get(k));
                    }
                        if (pn.compareTo(py) == 1)
                        {
                            py = one.subtract(py);
                            pn = one.subtract(pn);
                        }

                        pe = p.multiply(py).add(one.subtract(p).multiply(pn));

                        if (!(py.compareTo(one) == 0 && p.compareTo(one) == 0) &&
                                (pe.compareTo(one) == -1 && pe.compareTo(zero) == 1))
                        {
                            result = diagnosticValues.get(j).add(p.multiply(py).divide(pe, 22, RoundingMode.HALF_UP).subtract(p.multiply(one.subtract(py)).divide(one.subtract(pe), 22, RoundingMode.HALF_UP)));
                            diagnosticValues.set(j, result);
                        }

                        if (diagnosticValues.get(j).compareTo(max) == 1) {
                            max = diagnosticValues.get(j);
                            l_mostAccurateQuestion = BindingSymptomHypothesisTable.getSymptomNames().get(j);
                        }

                }
            }
        }
    return l_mostAccurateQuestion;
    }

    private void asnwered(String p_question, int p_answer)
    {
        for(int i = 0; i < BindingSymptomHypothesisTable.getSymptomNames().size(); i++)
        {
            ArrayList<String> l_listOfNames = BindingSymptomHypothesisTable.getSymptomNames();
                if(l_listOfNames.get(i) == p_question)
                {
                    for(int k = 0; k < BindingSymptomHypothesisTable.get_p1_list().get(i).size(); k++)
                    {
                        BigDecimal p = new BigDecimal(hypotheses.get(k).getHypothseisProbability());
                        BigDecimal py = new BigDecimal(BindingSymptomHypothesisTable.get_p1_list().get(i).get(k)).setScale(4, BigDecimal.ROUND_HALF_UP);
                        BigDecimal pn = new BigDecimal(BindingSymptomHypothesisTable.get_p2_list().get(i).get(k)).setScale(4, BigDecimal.ROUND_HALF_UP);
                        BigDecimal one = new BigDecimal("1.0000000000000000000000");

                        Log.e("value", p.toPlainString());
                        Log.e("value", py.toPlainString());
                        Log.e("value", pn.toPlainString());

                        MathContext mc = new MathContext(22);
                        BigDecimal resul11 = p.multiply(py);
                        BigDecimal resul22 = one.subtract(p);
                        BigDecimal resul33 = resul22.multiply(pn);
                        BigDecimal pe = resul11.add(resul33);

                        BigDecimal zero = new BigDecimal("0.0000000000000000000000");

                        if(p_answer == 4)
                        {
                            if (pe.compareTo(zero) == 1) {

                                //barylowi tbp[i] *= py/pe
                                BigDecimal div = py.divide(pe, mc);
                                BigDecimal result = p.multiply(div, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(result));
                                test.setResult(k, String.valueOf(result));
                                Log.e("onTak", result.toPlainString());
                            }
                        }

                        if(p_answer == 3)
                        {
                            if (pe.compareTo(zero) == 1) {
                                // barylowi tbp[i] *= 0.5* (py/pe) +0.5
                                BigDecimal half = new BigDecimal("0.5000000000000000000000");
                                BigDecimal resul2 = py.divide(pe, mc);
                                BigDecimal resul3 = half.multiply(resul2, mc);
                                BigDecimal result1 = resul3.add(half);
                                BigDecimal result = p.multiply(result1, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(result));
                                test.setResult(k, String.valueOf(result));
                                Log.e("onRaczejTak", result.toPlainString());
                            }
                        }

                        if(p_answer == 1)
                        {
                            if (pe.compareTo(one) == -1) {
                                // barylowi: raczej_nie: tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
                                BigDecimal half = new BigDecimal("0.5000000000000000000000");
                                BigDecimal result1 = one.subtract(py, mc);
                                BigDecimal result2 = one.subtract(pe, mc);
                                BigDecimal multi = half.multiply(result1, mc);
                                BigDecimal div = multi.divide(result2, mc);
                                BigDecimal result3 = div.add(half);
                                BigDecimal result = p.multiply(result3, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(result));
                                test.setResult(k, String.valueOf(result));
                                Log.e("onRaczejNie", result.toPlainString());
                            }
                        }

                        if(p_answer == 0)
                        {
                            if (pe.compareTo(one) == -1) {
                                // barylowi tbp[i] *= (1-py)/(1-pe)
                                BigDecimal resu1 = one.subtract(py, mc);
                                BigDecimal resu2 = one.subtract(pe, mc);
                                BigDecimal resu3 = resu1.divide(resu2, mc);
                                BigDecimal result = p.multiply(resu3, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(result));
                                test.setResult(k, String.valueOf(result));
                                Log.e("onNie", result.toPlainString());
                            }
                        }
                    }

                }
        }
    }

    private void handleNoMoreQuestions() {
        bMaybeYea.setEnabled(false);
        bMaybeNot.setEnabled(false);
        bYea.setEnabled(false);
        bNo.setEnabled(false);
        bDontKnow.setEnabled(false);
        bShowReult.setEnabled(true);

        ArrayList<BigDecimal> changed = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < hypotheses.size(); i++)
        {
            changed.add(new BigDecimal(hypotheses.get(i).getHypothseisProbability()));
            names.add(hypotheses.get(i).getHypothesisName());
        }

        if (changed.size() > 0)
        {
            BigDecimal result = changed.get(0);
            String name = names.get(0);

            for (int i = 0; i < changed.size(); i++){
                int res = result.compareTo(changed.get(i));
                if (res == -1)
                {
                    result = changed.get(i);
                    name = names.get(i);
                }
            }
            Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "result: " + name + ", val: " + result.toPlainString(), Toast.LENGTH_LONG).show();
        }
    }

    private void initTestObject()
    {
        ArrayList<String> probability = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < BindingSymptomHypothesisTable.get_names_list().size(); i++)
        {
            BindingSymptomHypothesisTable.set_symptom_name(symptoms.get(i).getQuestion());
        }

        for (int i = 0; i < hypotheses.size(); i++)
        {
            probability.add(hypotheses.get(i).getHypothseisProbability());
            names.add(hypotheses.get(i).getHypothesisName());
        }

        test.setPa(probability);
        test.setP1(BindingSymptomHypothesisTable.getBindingSymptomHypothesisP1());
        test.setP2(BindingSymptomHypothesisTable.getBindingSymptomHypothesisP2());
        test.setHM(names);
    }

    private void setIsAnswered(String p_question)
    {
        for(int i = 0; i < symptoms.size(); i++)
        {
            if(symptoms.get(i).getQuestion() == p_question)
            {
                symptoms.get(i).setIsAnswered(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertise_display);

        hypotheses = getIntent().getParcelableArrayListExtra("hypo");
        symptoms = getIntent().getParcelableArrayListExtra("symptoms");
        BindingSymptomHypothesisTable = getIntent().getParcelableExtra("bindings");

        initTestObject();

        bQuest = findViewById(R.id.tQuestion);
        bQuest.setMovementMethod(new ScrollingMovementMethod());

        bYea = findViewById(R.id.bYes);
        bNo = findViewById(R.id.bNo);
        bMaybeYea = findViewById(R.id.bMaybeYea);
        bMaybeNot = findViewById(R.id.bMaybeNot);
        bShowAnswers = findViewById(R.id.bShowAnswers);
        bDontKnow = findViewById(R.id.bDontKnow);
        bShowReult = findViewById(R.id.bShowResult);
        bShowReult.setEnabled(false);


        if (symptoms.size() != 0){
            String question = computeDiagnosticValue();
            displaySymptomQuestion("");
        }

        bShowAnswers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, ShowAnswers.class);
                i.putParcelableArrayListExtra("answers", answers);
                startActivity(i);
            }
            });

        bShowReult.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.mainActivity, ShowResult.class);
                i.putParcelableArrayListExtra("test", hypotheses);
                startActivity(i);
            }
        });

        bDontKnow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    asnwered(l_question, 2);
                    symptomQuestIndex++;
                    index++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }});

        bYea.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    //displaySymptomQuestion();
                    //symptoms.get(symptomQuestIndex).setIsAnswered(true);
                    asnwered(l_question, 4);
                    symptomQuestIndex++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }
        });

        bNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                   // symptoms.get(symptomQuestIndex).setIsAnswered(true);
                    asnwered(l_question, 0);
                    symptomQuestIndex++;
                    index++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }
        });

        bMaybeYea.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                     //String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                     String l_question = computeDiagnosticValue();
                     displaySymptomQuestion(l_question);
                     setIsAnswered(l_question);
                    // symptoms.get(symptomQuestIndex).setIsAnswered(true);
                     asnwered(l_question, 3);
                     symptomQuestIndex++;
                     index++;
                }
                 else
                 {
                     handleNoMoreQuestions();
                 }
            }
        });

        bMaybeNot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    //symptoms.get(symptomQuestIndex).setIsAnswered(true);
                    asnwered(l_question, 1);
                    symptomQuestIndex++;
                    index++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }
        });
    }

    public static  class ShowResult extends  AppCompatActivity
    {
        private ArrayList<Hypothesis> test;
        private TextView tResult;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_result);

            test = getIntent().getParcelableArrayListExtra("test");
            tResult = findViewById(R.id.tShowResult);
            tResult.setMovementMethod(new ScrollingMovementMethod());

            String msg = "";
            ArrayList<Hypothesis> l_test = test;

            for(int i = 0; i < l_test.size(); i++)
            {
                BigDecimal l_msg = new BigDecimal("0.0");
                String l_name = "";
                for(int j = 0; j < l_test.size(); j++)
                {
                    BigDecimal l_val = new BigDecimal(l_test.get(j).getHypothseisProbability());
                    if(l_msg.compareTo(l_val) == -1)
                    {
                        l_msg = l_val;
                        l_name = l_test.get(j).getHypothesisName();
                        l_test.remove(j);
                    }
                }
                msg += l_name + " " + l_msg.toPlainString() + '\n';
            }


//            for(int i = 0; i < test.size(); i++)
//            {
//                BigDecimal local = new BigDecimal(test.get(i).getHypothseisProbability());
//                String sLocal = test.get(i).getHypothesisName();
//                String ll = String.valueOf(local.toPlainString()).substring(0, 12);
//                msg += sLocal + " " + ll + '\n';
//            }

            tResult.setText(msg);
        }
    }

    public static class ShowAnswers extends AppCompatActivity {

        private ArrayList<Answers> answers = new ArrayList<>();
        private TextView tQuestions;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_answers);

            answers = getIntent().getParcelableArrayListExtra("answers");
            tQuestions = findViewById(R.id.tShowQuestions);
            tQuestions.setMovementMethod(new ScrollingMovementMethod());

            String quest = "";
            for (int i = 0; i < answers.size(); i++)
            {
                quest += answers.get(i).getQuestion() + '\t' + '\t' + '\t' +'\r';
                int tempAns = answers.get(i).getAnswer();

                if (tempAns == 4)
                {
                    quest += "tak" + '\n';
                }
                if (tempAns == 3)
                {
                    quest += "raczej tak" + '\n';
                }
                if (tempAns == 2)
                {
                    quest += "nie wiem" + '\n';
                }
                if (tempAns == 1)
                {
                    quest += "raczej nie" + '\n';
                }
                if (tempAns == 0)
                {
                    quest += "nie" + '\n';
                }
            }
            tQuestions.setText(String.valueOf(quest));
        }
    }
}
