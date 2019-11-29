package com.bayex.bayex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private BigDecimal one = new BigDecimal("1.0000000000000000000000");
    private BigDecimal zero = new BigDecimal("0.0000000000000000000000");
    private BigDecimal half = new BigDecimal("0.5000000000000000000000");

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

    private String computeDiagnosticValue() {
        BigDecimal p, py, pn, pe, result;
        BigDecimal max = new BigDecimal("-1.0");
        String l_mostAccurateQuestion = "";
        ArrayList<BigDecimal> values = new ArrayList<>();
        for (int i = 0; i < BindingSymptomHypothesisTable.getSymptomNames().size(); i++) {
            values.add(new BigDecimal("0.0"));
        }

        for (int i = 0; i < hypotheses.size(); i++)
        {
            p = new BigDecimal(hypotheses.get(i).getHypothseisProbability());

            for (int j = 0; j < BindingSymptomHypothesisTable.get_names_list().size(); j++) {
                // for po wszystkich wiazaniach hs
                    if (!symptoms.get(j).isAnswered() && BindingSymptomHypothesisTable.get_p1_list().get(j).size() > i) {
                        py = new BigDecimal(BindingSymptomHypothesisTable.get_p1_list().get(j).get(i));
                        pn = new BigDecimal(BindingSymptomHypothesisTable.get_p2_list().get(j).get(i));

                        if (pn.compareTo(py) == 1) {
                            py = one.subtract(py);
                            pn = one.subtract(pn);
                        }

                        pe = p.multiply(py).add(one.subtract(p).multiply(pn));

                        if (!(py.compareTo(one) == 0 && p.compareTo(one) == 0) && (pe.compareTo(one) == -1 && pe.compareTo(zero) == 1)) {
                            result = values.get(j).add(p.multiply(py).divide(pe, 22, RoundingMode.HALF_UP).subtract(p.multiply(one.subtract(py)).divide(one.subtract(pe), 22, RoundingMode.HALF_UP)));
                            values.set(j, result);
                        }

                        if (values.get(j).compareTo(max) == 1) {
                            max = values.get(j);
                            l_mostAccurateQuestion = BindingSymptomHypothesisTable.getSymptomNames().get(j);
                        }
                    }
            }
        }
    return l_mostAccurateQuestion;
    }

    private void asnwered(String p_question, int p_answer)
    {
        BigDecimal p;
        BigDecimal py;
        BigDecimal pn;

        for(int i = 0; i < BindingSymptomHypothesisTable.getSymptomNames().size(); i++)
        {
            ArrayList<String> l_listOfNames = BindingSymptomHypothesisTable.getSymptomNames();
                if(l_listOfNames.get(i) == p_question)
                {
                    for(int k = 0; k < BindingSymptomHypothesisTable.get_p1_list().get(i).size(); k++)
                    {
                        p = new BigDecimal(hypotheses.get(k).getHypothseisProbability());
                        py = new BigDecimal(BindingSymptomHypothesisTable.get_p1_list().get(i).get(k)).setScale(4, BigDecimal.ROUND_HALF_UP);
                        pn = new BigDecimal(BindingSymptomHypothesisTable.get_p2_list().get(i).get(k)).setScale(4, BigDecimal.ROUND_HALF_UP);

                        MathContext mc = new MathContext(22);
                        BigDecimal pe = p.multiply(py).add(one.subtract(p).multiply(pn));


                        if(p_answer == 4)
                        {
                            if (pe.compareTo(zero) == 1) {

                                //barylowi tbp[i] *= py/pe
                                BigDecimal div = py.divide(pe, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(p.multiply(div, mc)));
                                test.setResult(k, String.valueOf(p.multiply(div, mc)));
                            }
                        }

                        if(p_answer == 3)
                        {
                            if (pe.compareTo(zero) == 1) {
                                // barylowi tbp[i] *= 0.5* (py/pe) +0.5
                                BigDecimal resul2 = py.divide(pe, mc);
                                BigDecimal resul3 = half.multiply(resul2, mc);
                                BigDecimal result1 = resul3.add(half);
                                BigDecimal result = p.multiply(result1, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(result));
                                test.setResult(k, String.valueOf(result));
                            }
                        }

                        if(p_answer == 1)
                        {
                            if (pe.compareTo(one) == -1) {
                                // barylowi: raczej_nie: tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
                                BigDecimal result1 = one.subtract(py, mc);
                                BigDecimal result2 = one.subtract(pe, mc);
                                BigDecimal multi = half.multiply(result1, mc);
                                BigDecimal div = multi.divide(result2, mc);
                                BigDecimal result3 = div.add(half);
                                BigDecimal result = p.multiply(result3, mc);
                                hypotheses.get(k).setHypothesisProbability(String.valueOf(result));
                                test.setResult(k, String.valueOf(result));
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
        //bShowReult.setEnabled(true);

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
        //bShowReult.setEnabled(false);


        if (symptoms.size() != 0){
            String question = computeDiagnosticValue();
            displaySymptomQuestion(question);
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
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    asnwered(l_question, 2);
                    addAnswer(l_question, "", 2);
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
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    asnwered(l_question, 4);
                    addAnswer(l_question, "", 4);
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
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    asnwered(l_question, 0);
                    addAnswer(l_question, "", 0);
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
                     String l_question = computeDiagnosticValue();
                     displaySymptomQuestion(l_question);
                     setIsAnswered(l_question);
                     asnwered(l_question, 3);
                     addAnswer(l_question, "", 3);
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
                    String l_question = computeDiagnosticValue();
                    displaySymptomQuestion(l_question);
                    setIsAnswered(l_question);
                    asnwered(l_question, 1);
                    addAnswer(l_question, "", 1);
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

        public static HashMap<String, String> sortByValue(HashMap<String, String> hm)
        {
            // Create a list from elements of HashMap
            List<Map.Entry<String, String> > list =
                    new LinkedList<Map.Entry<String, String> >(hm.entrySet());

            // Sort the list
            Collections.sort(list, new Comparator<Map.Entry<String, String> >() {
                public int compare(Map.Entry<String, String> o1,
                                   Map.Entry<String, String> o2)
                {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });

            // put data from sorted list to hashmap
            HashMap<String, String> temp = new LinkedHashMap<String, String>();
            for (Map.Entry<String, String> aa : list) {
                temp.put(aa.getKey(), aa.getValue());
            }
            return temp;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_result);

            test = getIntent().getParcelableArrayListExtra("test");
            tResult = findViewById(R.id.tShowResult);
            tResult.setMovementMethod(new ScrollingMovementMethod());

            String msg = "";
            HashMap<String, String> tmp = new HashMap<>();

            for (int i = 0; i < test.size(); ++i)
            {
                tmp.put(test.get(i).getHypothesisName(), test.get(i).getHypothseisProbability());
            }
            Map<String, String> sorted = sortByValue(tmp);


            for (Map.Entry<String, String> entry : sorted.entrySet())
            {
                msg += entry.getKey() + '\t' + '\t' + '\t' + '\r' + entry.getValue();
            }
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
            tQuestions.setText(quest);
        }
    }
}
