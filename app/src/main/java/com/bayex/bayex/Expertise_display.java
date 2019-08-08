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
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.Bayex.Symptom;
import com.bayex.bayex.Bayex.Test;
import java.util.ArrayList;

//TODO: piori calculation handle

public class Expertise_display extends AppCompatActivity {

    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptoms = new ArrayList<>();
    private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
    private ArrayList<BindingSymptomHypothesis> BindingSymptomHypothesisTable = new ArrayList<>();

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

    private void handleYesAnswerToGeneralSymptom(String p_question)
    {
        //TODO: handle click
        addAnswer(p_question, 4);
xx` `
    }

    private int afterAns(int value)
    {
        int next, il, v = 0;
        String msg;

        if (value == -2)
        {

        }
        return 1;
    }

    void whichQue()
    {}

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


    private void handleNoMoreQuestions()
    {
        Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "No more questions left", Toast.LENGTH_SHORT).show();
    }

    private void initTestObject()
    {
        ArrayList<String> probability = new ArrayList<>();
        ArrayList<ArrayList<String>> p1 = new ArrayList<>();
        ArrayList<ArrayList<String>> p2 = new ArrayList<>();
        for (int i = 0; i < hypotheses.size(); i++)
        {
            probability.add(hypotheses.get(i).getHypothseisProbability());
        }

        for (int i = 0; i < BindingSymptomHypothesisTable.size(); i++)
        {
            p1.add(BindingSymptomHypothesisTable.get(i).getBindingSymptomHypothesisP1());
            p2.add(BindingSymptomHypothesisTable.get(i).getBindingSymptomHypothesisP2());
        }
        test.setPa(probability);
        test.setP1(p1);
        test.setP2(p2);
    }

    private void calculateCurrentProbability(int p_symptomNumber, int p_answer)
    {
        double p = 0.0, py = 0.0, pn = 0.0, pe = 0.0;
        ArrayList<String> ap = test.getPaList();
        ArrayList<String> p1 = test.getP1List().get(p_symptomNumber);
        ArrayList<String> p2 = test.getP2List().get(p_symptomNumber);

        ArrayList<String> tmppa = new ArrayList<>();

        for (int i = 0; i < p1.size(); i++)
        {
            p = Double.parseDouble(ap.get(i));
            py = Double.parseDouble(p1.get(i));
            pn = Double.parseDouble(p2.get(i));
            pe = p * py + (1 - p) * pn;

            switch (p_answer)
            {
                case 0://answer: no
                {
                    if (pe < 1.0)
                    {
                        Double pa = Double.parseDouble(test.getPaList().get(i));
                        pa *= (1 - py) / (1 - pe);
                        tmppa.add(String.valueOf(pa));
                        test.setPa(tmppa);
                        //TODO: modify pa[i]
                    }
                }
                break;

                case 1: //answer rather no
                {
                    if (pe < 1.0)
                    {
                        Double pa = Double.parseDouble(test.getPaList().get(i));
                        pa *= 0.5 * (1 - py) / (1 - pe) + 0.5;
                        tmppa.add(String.valueOf(pa));
                        test.setPa(tmppa);
                        //TODO: modify pa[i]
                    }
                }
                break;

                case 2: //answer: don't know
                break;

                case 3: //answer: rather yea
                {
                    if (pe > 0.0)
                    {
                        Double pa = Double.parseDouble(test.getPaList().get(i));
                        pa *= 0.5 * (py / pe) + 0.5;
                        tmppa.add(String.valueOf(pa));
                        test.setPa(tmppa);
                        //TODO: modify pa[i]
                    }
                }
                break;

                case 4: //answer: yea
                {
                    if (pe > 0.0)
                    {
                        Double pa = Double.parseDouble(test.getPaList().get(i));
                        pa *= py / pe;
                        tmppa.add(String.valueOf(pa));
                        test.setPa(tmppa);
                        //TODO: modify pa[i]
                    }
                }
                break;
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expertise_display);

        hypotheses = getIntent().getParcelableArrayListExtra("hypo");
        symptoms = getIntent().getParcelableArrayListExtra("symptoms");
        generalSymptoms = getIntent().getParcelableArrayListExtra("general");
        BindingSymptomHypothesisTable = getIntent().getParcelableArrayListExtra("bindings");

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
            public void onClick(View v) {
                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
                {
                    //TODO: try to modify algorithm to wait with metaknowleage questions until they are needed
                    //TODO: determine how to modify apriori on YES click
                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
                    handleDontKnowAnswerToGeneralSymptom(l_question);
                    handleYesAnswerToGeneralSymptom(l_question);
                }
                else if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleDontKnowAnswerToGeneralSymptom(l_question);
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
                    //TODO: try to modify algorithm to wait with metaknowleage questions until they are needed
                    //TODO: determine how to modify apriori on YES click
                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
                    displayGeneralSymptomQuestion(l_question);
                    handleYesAnswerToGeneralSymptom(l_question);
                }
                else if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleYesAnswerToGeneralSymptom(l_question);
                }
                else
                {
                    handleNoMoreQuestions();
                }
            }
        });



        //for now only yes button implementation
        bNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (generalSymptoms.size() != 0 && validateIfAnyGeneralSymptomsQuestionsLeft())
                {
                    //TODO: try to modify algorithm to wait with metaknowleage questions until they are needed
                    //TODO: determine how to modify apriori on YES click
                    String l_question = generalSymptoms.get(generalQuestIndex).getGeneralSymptomQuestion();
                    displayGeneralSymptomQuestion(l_question);
                    handleNoAnswerToGeneralSymptom(l_question);
                }
                else if (symptoms.size() != 0 && validateIfAnySymptomsQuestionsLeft()){
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleNoAnswerToGeneralSymptom(l_question);
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
                    //TODO: determine how to choose next question
                    //TODO: determine how to modify apriori on YES click
                    String l_question = symptoms.get(symptomQuestIndex).getQuestion();
                    displaySymptomQuestion();
                    handleRatherNoAnswerToGeneralSymptom(l_question);
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
