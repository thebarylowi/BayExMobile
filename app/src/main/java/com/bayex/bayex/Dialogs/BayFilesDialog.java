package com.bayex.bayex.Dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import com.bayex.bayex.BaseSelectedActivity;
import com.bayex.bayex.Bayex.BindingsOfMetaknowledgeQuestions;
import com.bayex.bayex.Bayex.BindingSymptomHypothesis;
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.Bayex.Symptom;
import com.bayex.bayex.MainActivity;
import com.bayex.bayex.Utils.Files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BayFilesDialog {

    private AlertDialog bayFileDialog;
    private Files files;
    private ArrayList<Hypothesis> hypotheses = new ArrayList<>();
    private ArrayList<Symptom> symptomes = new ArrayList<>();
    private ArrayList<GeneralSymptom> generalSymptoms = new ArrayList<>();
    private ArrayList<BindingsOfMetaknowledgeQuestions> bindingsOfMetaknowledgeQuestions = new ArrayList<>();
    private BindingSymptomHypothesis BindingSymptomHypothesisTable = new BindingSymptomHypothesis();
    public static File baySelectedBase;

    public BayFilesDialog(final ArrayList<File> bayFileArray){
        files = new Files();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.mainActivity.getApplicationContext());
        builder.setTitle("Bazy wiedzy");

        ArrayList<String> namesArray = new ArrayList<>();
        for(int i = 0; i < bayFileArray.size(); i++){
            namesArray.add(bayFileArray.get(i).getName());
        }
        final Object[] titles = namesArray.toArray();
        final CharSequence[] _titles = new CharSequence[titles.length];
        for(int i = 0; i< titles.length; i++){
            _titles[i] = titles[i].toString();
        }
        builder.setItems(_titles, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baySelectedBase = bayFileArray.get(which);
                try {
                    files.fullyReadFileToClasses(new File(BayFilesDialog.baySelectedBase.getAbsolutePath()), hypotheses, symptomes, generalSymptoms, bindingsOfMetaknowledgeQuestions, BindingSymptomHypothesisTable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(MainActivity.mainActivity, BaseSelectedActivity.class);
                i.putParcelableArrayListExtra("hypo", hypotheses);
                i.putParcelableArrayListExtra("symptoms",symptomes);
                i.putParcelableArrayListExtra("general",generalSymptoms);
                i.putExtra("bindings",BindingSymptomHypothesisTable);// putParcelableExtra("bindings",BindingSymptomHypothesisTable);
                MainActivity.mainActivity.startActivity(i);
            }
        });
        bayFileDialog = builder.create();
        bayFileDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        bayFileDialog.show();
    }
}
