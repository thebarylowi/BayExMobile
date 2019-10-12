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
import java.math.RoundingMode;
import java.util.ArrayList;

//TODO: piori calculation handle

public class Expertise_display extends AppCompatActivity {

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptoms = new ArrayList<>();
    private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
    private BindingSymptomHypothesis BindingSymptomHypothesisTable = new BindingSymptomHypothesis();

    private Test test = new Test();

    private ArrayList<Answers> answers = new ArrayList<>();

    private Button bYea;
    private Button bNo;
    private Button bMaybeYea;
    private Button bMaybeNot;
    private Button bDontKnow;

    private Button bShowAnswers;

    private int generalQuestIndex = 0;
    private int symptomQuestIndex = 0;
    private int generalIndex = 0;
    private int index = 0;

    private TextView bQuest;

    private void addAnswer(String p_question, int p_answer)
    {
        Answers temp = new Answers(p_question, p_answer);
        answers.add(temp);
        generalIndex++;
    }

    private void displaySymptomQuestion()
    {
        bMaybeYea.setEnabled(true);
        bMaybeNot.setEnabled(true);
        bQuest.setText(String.valueOf(symptoms.get(symptomQuestIndex).getQuestion()));
        symptomQuestIndex++;
    }

    private void displayGeneralSymptomQuestion(String p_question)
    {
        bMaybeYea.setEnabled(false);
        bMaybeNot.setEnabled(false);
        bQuest.setText(String.valueOf(p_question));
        generalQuestIndex++;
    }

    private boolean validateIfAnyGeneralSymptomsQuestionsLeft()
    {
        return generalQuestIndex < generalSymptoms.size();
    }

    private boolean validateIfAnySymptomsQuestionsLeft()
    {
        return symptomQuestIndex < symptoms.size();
    }

    private  void handleYes(String p_question)
    {
        addAnswer(p_question, 4);
    }

    private void handleYesAnswerToGeneralSymptom(String p_question)
    {
        addAnswer(p_question, 10);
    }

    private void handleRatherYesAnswerToGeneralSymptom(String p_question)
    {
        //TODO: handle click
        addAnswer(p_question, 3);
    }

    private void handleDontKnowAnswerToGeneralSymptom(String p_question)
    {
        //TODO: handle click
        addAnswer(p_question, 2);
    }

    private void handleRatherNoAnswerToGeneralSymptom(String p_question)
    {
        addAnswer(p_question, 1);
    }

    private void handleNoAnswerToGeneralSymptom(String p_question)
    {
        addAnswer(p_question, 0);
    }

    private void handleNo(String p_question)
    {
        addAnswer(p_question, 0);
    }

    private void po_odpowiedzi() {
        for (int i = 0; i < answers.size(); i++) {
            Answers local = answers.get(i);
            int odp = local.getAnswer();
            for (int j = 0; j < hypotheses.size(); j++)
            {
                BigDecimal p = test.getPaList().get(j);
                BigDecimal py = test.getP1List().get(j);
                BigDecimal pn = test.getP2List().get(j);
                BigDecimal one = new BigDecimal("1");
                BigDecimal res1 = one.subtract(p);
                BigDecimal res2 = p.multiply(py);
                BigDecimal res3 = res1.multiply(pn);
                BigDecimal pe = res2.add(res3);
                BigDecimal zero = new BigDecimal("0");

                if (odp == 4)
                {
                        if (pe.compareTo(zero) == 1) {
                            //barylowi tbp[i] *= py/pe
                            BigDecimal div = py.divide(pe, 20, RoundingMode.HALF_UP);
                            BigDecimal result = p.multiply(div);
                            test.setBPa(j, result);
                        }
                }

                if (odp == 3)
                {
                    if (pe.compareTo(zero) == 1) {
                        // barylowi tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
                        BigDecimal half = new BigDecimal("0.5");
                        BigDecimal resul1 = one.subtract(py);
                        BigDecimal resul2 = half.multiply(resul1);
                        BigDecimal resul3 = one.subtract(pe);
                        BigDecimal div = resul2.divide(resul3, 19, RoundingMode.HALF_UP);
                        BigDecimal result = div.add(half);
                        test.setBPa(j, result);
                    }
                }

                if (odp == 2)
                {
                    BigDecimal temp = test.getPaList().get(j);
                    test.setBPa(j, temp);
                }

                if (odp == 1)
                {
                    if (pe.compareTo(one) == -1)
                    {
                        // barylowi: raczej_nie: tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
                        BigDecimal half = new BigDecimal("0.5");
                        BigDecimal result1 = one.subtract(py);
                        BigDecimal result2 = one.subtract(pe);
                        BigDecimal result3 = half.multiply(result1).divide(result2, 19, RoundingMode.HALF_UP);
                        BigDecimal result = result3.add(half);
                        test.setBPa(j, result);
                    }
                }

                if (odp == 0)
                {
                    //handle nie
                    if (pe.compareTo(one) == -1)
                    {
                        // barylowi tbp[i] *= (1-py)/(1-pe)
                        BigDecimal resu1 = one.subtract(py);
                        BigDecimal resu2 = one.subtract(pe);
                        BigDecimal resu3 = resu1.divide(resu2, 20, RoundingMode.HALF_UP);
                        BigDecimal result = p.multiply(resu3);
                        test.setBPa(j, result);
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

        po_odpowiedzi();
        ArrayList<BigDecimal> changed = test.getResult();
        ArrayList<String> names = test.getHN();

        if (changed.size() > 0)
        {
            BigDecimal result = changed.get(0);
            String name = names.get(0);

            for (int i = 0; i < changed.size(); i++){
                if (result.compareTo(changed.get(i)) == -1)
                {
                    result = changed.get(i);
                    name = names.get(i);
                }
            }
            Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "result: " + name, Toast.LENGTH_SHORT).show();
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
        generalSymptoms = getIntent().getParcelableArrayListExtra("general");
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


        if (generalSymptoms.size() != 0)
        {
            String l_question = generalSymptoms.get(0).getGeneralSymptomQuestion();
            displayGeneralSymptomQuestion(l_question);
        }
        else if (symptoms.size() != 0){
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

        bDontKnow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //barylowi
                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
                {
                    //TODO: try to modify algorithm to wait with metaknowleage questions until they are needed
                    //TODO: determine how to modify apriori on YES click
                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
                    handleDontKnowAnswerToGeneralSymptom(l_question);
                    index++;
                }
                else if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
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
                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
                {
                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
                    displayGeneralSymptomQuestion(l_question);
                    handleYesAnswerToGeneralSymptom(l_question);
                    index++;
                }
                else if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleYes(l_question);
                    index++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }
        });

        bNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
                {
                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
                    displayGeneralSymptomQuestion(l_question);
                    handleNoAnswerToGeneralSymptom(l_question);
                    index++;
                }
                else if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleNo(l_question);
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

//                     //barylowi - why app is terminated on last q???
//                     BigDecimal p = test.getPaList().get(index);
//                     BigDecimal py = test.getP1List().get(index);
//                     BigDecimal pn = test.getP2List().get(index);
//                     BigDecimal one = new BigDecimal("1");
//                     BigDecimal zero = new BigDecimal("0");
//                     BigDecimal res1 = one.subtract(p);
//                     BigDecimal pe = p.multiply(py).add(res1).multiply(pn);
//
//                     if (pe.compareTo(zero) == 1)
//                     {
//                         // barylowi tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
//                         BigDecimal half = new BigDecimal("0.5");
//                         BigDecimal resul1 = one.subtract(py);
//                         BigDecimal resul2 = half.multiply(resul1);
//                         BigDecimal resul3 = one.subtract(pe);
//                         BigDecimal div = resul2.divide(resul3, 19, RoundingMode.HALF_UP);
//                         BigDecimal result = div.add(half);
//                         test.setBPa(result);
////                         Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "result: " + result, Toast.LENGTH_SHORT).show();
//                     }
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
//
//                    //barylowi
//                    BigDecimal p = test.getPaList().get(index);
//                    BigDecimal py = test.getP1List().get(index);
//                    BigDecimal pn = test.getP2List().get(index);
//                    BigDecimal one = new BigDecimal("1");
//                    BigDecimal res1 = one.subtract(p);
//                    BigDecimal pe = p.multiply(py).add(res1).multiply(pn);
//
//                    if (pe.compareTo(one) == -1)
//                    {
//                        // barylowi: raczej_nie: tbp[i] *= 0.5* (1-py)/(1-pe) +0.5
//                        BigDecimal half = new BigDecimal("0.5");
//                        BigDecimal result1 = one.subtract(py);
//                        BigDecimal result2 = one.subtract(pe);
//                        BigDecimal result3 = half.multiply(result1).divide(result2, 19, RoundingMode.HALF_UP);
//                        BigDecimal result = result3.add(half);
//                        test.setBPa(result);
////                        Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "result: " + result, Toast.LENGTH_SHORT).show();
//                    }

                    index++;
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }
        });
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
