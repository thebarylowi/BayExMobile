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
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.Bayex.Symptom;
import com.bayex.bayex.Bayex.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

//TODO: piori calculation handle

public class Expertise_display extends AppCompatActivity {

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptoms = new ArrayList<>();
    //private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
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

    private void displaySymptomQuestion()
    {
        bMaybeYea.setEnabled(true);
        bMaybeNot.setEnabled(true);
        bQuest.setText(String.valueOf(symptoms.get(symptomQuestIndex).getQuestion()));
    }

    private void displayGeneralSymptomQuestion(String p_question)
    {
        bMaybeYea.setEnabled(false);
        bMaybeNot.setEnabled(false);
        bQuest.setText(String.valueOf(p_question));
    }

//    private boolean validateIfAnyGeneralSymptomsQuestionsLeft()
//    {
//        return generalQuestIndex < generalSymptoms.size();
//    }

    private boolean validateIfAnySymptomsQuestionsLeft()
    {
        return symptomQuestIndex < symptoms.size();
    }

    private  void handleYes(String p_question, String p_name)
    {
        addAnswer(p_question, p_name, 4);
    }

//    private void handleYesAnswerToGeneralSymptom(String p_question)
//    {
//        addAnswer(p_question, "", 10);
//    }

    private void handleRatherYesAnswerToGeneralSymptom(String p_question)
    {
        //TODO: handle click
        addAnswer(p_question, "", 3);
    }

    private void handleDontKnowAnswerToGeneralSymptom(String p_question)
    {
        //TODO: handle click
        addAnswer(p_question, "", 2);
    }

    private void handleRatherNoAnswerToGeneralSymptom(String p_question)
    {
        addAnswer(p_question, "", 1);
    }

//    private void handleNoAnswerToGeneralSymptom(String p_question)
//    {
//        addAnswer(p_question, "", 0);
//    }

    private void handleNo(String p_question)
    {
        addAnswer(p_question, "",0);
    }

    private void po_odpowiedzi() {
        for (int i = 0; i < answers.size(); i++) {
            Answers local = answers.get(i);
            int odp = local.getAnswer();
            String l_name = answers.get(i).getName();

            ArrayList<String> l_listOfNames = BindingSymptomHypothesisTable.get_names_list().get(i);

            for (int k = 0; k < l_listOfNames.size(); k++) {
                for (int j = 0; j < hypotheses.size(); j++) {
                    if (Integer.parseInt(l_listOfNames.get(k)) == j){

                            BigDecimal p = test.getPaList().get(j);
                            BigDecimal py = new BigDecimal(BindingSymptomHypothesisTable.get_p1_list().get(i).get(k));
                            BigDecimal pn = new BigDecimal(BindingSymptomHypothesisTable.get_p2_list().get(i).get(k));
                            BigDecimal one = new BigDecimal("1.0000000000000000000000");

                            MathContext mc = new MathContext(22);
                            BigDecimal res1 = one.subtract(p, mc);
                            BigDecimal res2 = p.multiply(py);
                            BigDecimal res3 = res1.multiply(pn);
                            BigDecimal pe = res2.add(res3);
                            BigDecimal zero = new BigDecimal("0.0000000000000000000000");

                            if (odp == 4) {
                                if (pe.compareTo(zero) == 1) {
                                    //barylowi tbp[i] *= py/pe
                                    BigDecimal div = py.divide(pe, mc);
                                    BigDecimal result = p.multiply(div, mc);
                                    test.setResult(j, String.valueOf(result));
                                    continue;
                                }
                            }

                            if (odp == 3) {
                                if (pe.compareTo(zero) == 1) {
                                    // barylowi tbp[i] *= 0.5* (py/pe) +0.5
                                    BigDecimal half = new BigDecimal("0.5000000000000000000000");
                                    BigDecimal resul2 = py.divide(pe, mc);
                                    BigDecimal resul3 = half.multiply(resul2, mc);
                                    BigDecimal result1 = resul3.add(half);
                                    BigDecimal result = p.multiply(result1, mc);
                                    test.setResult(j, String.valueOf(result));
                                    continue;
                                }
                            }

                            if (odp == 1) {
                                if (pe.compareTo(one) == -1) {
                                    // barylowi: raczej_nie: tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
                                    BigDecimal half = new BigDecimal("0.5000000000000000000000");
                                    BigDecimal result1 = one.subtract(py, mc);
                                    BigDecimal result2 = one.subtract(pe, mc);
                                    BigDecimal multi = half.multiply(result1, mc);
                                    BigDecimal div = multi.divide(result2, mc);
                                    BigDecimal result3 = div.add(half);
                                    BigDecimal result = p.multiply(result3, mc);
                                    test.setResult(j, String.valueOf(result));
                                    continue;
                                }
                            }

                            if (odp == 0) {
                                //handle nie
                                if (pe.compareTo(one) == -1) {
                                    // barylowi tbp[i] *= (1-py)/(1-pe)
                                    BigDecimal resu1 = one.subtract(py, mc);
                                    BigDecimal resu2 = one.subtract(pe, mc);
                                    BigDecimal resu3 = resu1.divide(resu2, mc);
                                    BigDecimal result = p.multiply(resu3, mc);
                                    test.setResult(j, String.valueOf(result));
                                    continue;
                                }
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

        po_odpowiedzi();
        ArrayList<BigDecimal> changed = test.getPaList();
        ArrayList<String> names = test.getHN();

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
            Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "result: " + name + ", val: " + result, Toast.LENGTH_LONG).show();
        }
    }

    private void initTestObject()
    {
        ArrayList<String> probability = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertise_display);

        hypotheses = getIntent().getParcelableArrayListExtra("hypo");
        symptoms = getIntent().getParcelableArrayListExtra("symptoms");
       // generalSymptoms = getIntent().getParcelableArrayListExtra("general");
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


//        if (generalSymptoms.size() != 0)
//        {
//            String l_question = generalSymptoms.get(0).getGeneralSymptomQuestion();
//            displayGeneralSymptomQuestion(l_question);
//            generalQuestIndex++;
//        }
        if (symptoms.size() != 0){
            //TODO: determine how to choose first question to show
            displaySymptomQuestion();
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
                // barylowi1
                Intent i = new Intent(MainActivity.mainActivity, ShowResult.class);
                i.putExtra("test", test);
                startActivity(i);
            }
        });

        bDontKnow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //barylowi
//                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
//                {
//                    //TODO: try to modify algorithm to wait with metaknowleage questions until they are needed
//                    //TODO: determine how to modify apriori on YES click
//                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
//                    handleDontKnowAnswerToGeneralSymptom(l_question);
//                    index++;
//                }
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleDontKnowAnswerToGeneralSymptom(l_question);
                    index++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }});

        bYea.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
//                {
//                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
//                    displayGeneralSymptomQuestion(l_question);
//                    handleYesAnswerToGeneralSymptom(l_question);
//                    generalQuestIndex++;
//                    index++;
//                }
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    String l_name = symptoms.get(symptomQuestIndex).getSymptomName();
                    displaySymptomQuestion();
                    handleYes(l_question, l_name);
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
//                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
//                {
//                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
//                    displayGeneralSymptomQuestion(l_question);
//                    handleNoAnswerToGeneralSymptom(l_question);
//                    generalQuestIndex++;
//                    index++;
//                }
                if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleNo(l_question);
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
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                     String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                     displaySymptomQuestion();
                     handleRatherYesAnswerToGeneralSymptom(l_question);
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
//                    //TODO: determine how to choose next question
//                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleRatherNoAnswerToGeneralSymptom(l_question);
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
        private Test test;
        private TextView tResult;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_result);

            test = getIntent().getParcelableExtra("test");
            tResult = findViewById(R.id.tShowResult);
            tResult.setMovementMethod(new ScrollingMovementMethod());

            String msg = "";
            //BigDecimal tmp = test.getPaList().get(0);
            for(int i = 0; i < test.getPaList().size(); i++)
            {
                BigDecimal local = test.getPaList().get(i);
                String sLocal = test.getHN().get(i);
                String ll = String.valueOf(local.toPlainString()).substring(0, 12);
                msg += sLocal + " " + ll + '\n';
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
            tQuestions.setText(String.valueOf(quest));
        }
    }
}
