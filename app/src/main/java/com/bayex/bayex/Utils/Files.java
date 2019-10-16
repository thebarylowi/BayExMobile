package com.bayex.bayex.Utils;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.bayex.bayex.Bayex.BindingsOfMetaknowledgeQuestions;
import com.bayex.bayex.Bayex.BindingSymptomHypothesis;
import com.bayex.bayex.Bayex.GeneralSymptom;
import com.bayex.bayex.Bayex.KnowledgeBase;
import com.bayex.bayex.Bayex.Symptom;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.bayex.bayex.Bayex.Hypothesis;
import com.bayex.bayex.MainActivity;

public class Files {


    public Files() {
    }

    public Boolean createFolder(String name) {
        File myDirectory;
        if (name.length() > 0) {
            myDirectory = new File(getApplicationFolder(), name);
        } else {
            myDirectory = new File(getApplicationFolder());
        }
        if (!isFolderExists(myDirectory)) {
            if (!myDirectory.mkdir()) {
                Log.e(Consts.NAMES.appName, "Failed to create " + name + " folder");
                return false;
            }
            Log.d(Consts.NAMES.appName, "Folder " + name + " created");
            return true;
        }
        return false;
    }

    public String getApplicationFolder() {
        String mainDir = Environment.getExternalStorageDirectory().toString();
        mainDir += "/" + Consts.NAMES.mainFolderName;
        return mainDir;
    }

    public Boolean isFolderExists(File directory) {
        if (directory.exists()) {
            Log.d(Consts.NAMES.appName, "Folder " + directory.getName() + " already exists");
            return true;
        }
        return false;
    }

    public ArrayList<File> findBayFiles() {
        ArrayList<File> bayFilesArray = new ArrayList<>();
        File directory = new File(getApplicationFolder());
        File[] filesInDirectory = directory.listFiles();
        for (int i = 0; i < filesInDirectory.length; i++) {
            if (!filesInDirectory[i].isDirectory()) {
                if (filesInDirectory[i].getName().endsWith(".TXT") || filesInDirectory[i].getName().endsWith(".txt")) {
                    bayFilesArray.add(filesInDirectory[i]);
                }
            }
        }
        if (bayFilesArray.size() > 0) {
            return bayFilesArray;
        }
        return null;
    }

    private String readCRLFLine(BufferedReader p_buffer) throws IOException {
        StringBuilder result = new StringBuilder();
        char cr = 'a';
        char lf;
        try {
            while (((lf = (char) p_buffer.read()) != '\n') || (cr != '\r')) {
                cr = lf;
                result.append(lf);
            }
            result.deleteCharAt(result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private void readBaseInfo(BufferedReader br) throws IOException {
        br.readLine();
        br.readLine();

        String l_baseNumber = br.readLine();
        String l_baseVersion = br.readLine();

        String l_descriptionReader = readCRLFLine(br);
        String[] l_description = l_descriptionReader.split("\t");
        String l_baseDescription = "";

        if (Integer.parseInt(l_description[0]) != 0)
        {
            l_baseDescription = l_description[1];
        }
        KnowledgeBase.setBaseNumber(Double.parseDouble(l_baseNumber));
        KnowledgeBase.setBaseVersion(Double.parseDouble(l_baseVersion));
        KnowledgeBase.setDecription(l_baseDescription);
    }

    private void readHypothesis(BufferedReader br, ArrayList<Hypothesis> h) throws IOException {
        br.readLine();
        String hypothesisCountString = readCRLFLine(br);
        String hypothesisCount[] = hypothesisCountString.split("\t");
        //Toast.makeText(MainActivity.mainActivity.getApplicationContext(), hypothesisCount[0], Toast.LENGTH_LONG).show();

        for (int i = 0; i < Integer.parseInt(hypothesisCount[0]); i++) {
            String temp = readCRLFLine(br);
            String[] hypothesis = temp.split("\t");
            if (hypothesis[0] != "0") {
                String hypothesisName = hypothesis[1];
                String hypothesisProbability = hypothesis[2];
                String hypothesisDescription = "";
                if (hypothesis.length == 5) {
                    hypothesisDescription = hypothesis[4];
                }
                    h.add(new Hypothesis(hypothesisName, hypothesisDescription, hypothesisProbability));
            }
        }
    }

    private void readSymptoms(BufferedReader br, ArrayList<Symptom> s) throws IOException {
        br.readLine();
        String symptomsCountString = readCRLFLine(br);
        int symptomsCount = Integer.parseInt(symptomsCountString);

        for (int i = 0; i < symptomsCount; i++){
            String temp = readCRLFLine(br);
            String[] symptomsContent = temp.split("\t");
            String symptomName = symptomsContent[1];
            String symptomQuestion = symptomsContent[3];

            Symptom element = new Symptom(symptomName, symptomQuestion, "");
            if (Integer.parseInt(symptomsContent[4]) != 0){
                String symptomComment = symptomsContent[5];
                element.setSymptomDescription(symptomComment);
            }
            s.add(element);
        }
    }

    private void readGeneralSymptoms(BufferedReader br, ArrayList<GeneralSymptom> gs) throws IOException {
        br.readLine();
        String generalSymptomsCountString = readCRLFLine(br);
        String[] generalSymptomsCount = generalSymptomsCountString.split("\t");

        if (generalSymptomsCount[0] != "0") {
            for (int i = 0; i < Integer.parseInt(generalSymptomsCount[0]); i++) {
                String temp = br.readLine();
                String[] generalSymptoms = temp.split("\t");
                String generalSymptomName = generalSymptoms[1];
                String generalSymptomQuestion = generalSymptoms[3];

                GeneralSymptom generalSymptom = new GeneralSymptom(generalSymptomName, generalSymptomQuestion, "");
                if (Integer.parseInt(generalSymptoms[4]) != 0) {
                    String generalSymptomDescription = generalSymptoms[5];
                    generalSymptom.setGeneralSymptomDescription(generalSymptomDescription);
                }
                gs.add(generalSymptom);
            }
        }
    }

    private void readBindingsOfMetaknowledgeQuestions(BufferedReader br, ArrayList<BindingsOfMetaknowledgeQuestions> b) throws IOException {
        br.readLine();
        String bindingsOfMetaknowledgeQuestionsCountString = readCRLFLine(br);
        if (Integer.parseInt(bindingsOfMetaknowledgeQuestionsCountString) != 0) {
            for (int i = 0; i < Integer.parseInt(bindingsOfMetaknowledgeQuestionsCountString); i++)
            {
                String count = readCRLFLine(br);
                if (Integer.parseInt(count) != 0){
                    for (int j = 0; j < Integer.parseInt(count); j++)
                    {
                        String content = readCRLFLine(br);
                        String[] bindingsContent = content.split("\t");
                        b.add(new BindingsOfMetaknowledgeQuestions(Integer.parseInt(bindingsContent[0]), bindingsContent[1].charAt(0)));
                    }
                }
            }
        }
    }

    private void readBindingsOfSymptomsAndHypothesis(BufferedReader br, BindingSymptomHypothesis bsht) throws IOException {
        br.readLine();
        String BindingOfHypothesisAndSymptomsCountString = readCRLFLine(br);

        for (int i = 0; i < Integer.parseInt(BindingOfHypothesisAndSymptomsCountString); i++){
            String localCount = readCRLFLine(br);
            ArrayList<String> l_names = new ArrayList<>();
            ArrayList<String> l_p1_list = new ArrayList<>();
            ArrayList<String> l_p2_list = new ArrayList<>();

            for (int j = 0; j < Integer.parseInt(localCount); j++)
            {
                String content = readCRLFLine(br);
                String[] splittedContent = content.split("\t");

                l_names.add(splittedContent[0]);
                l_p1_list.add(splittedContent[1]);
                l_p2_list.add(splittedContent[2]);

                bsht.setBindingSymptomHypothesisNumber(splittedContent[0]);
                bsht.setBindingSymptomHypothesisP1(splittedContent[1]);
                bsht.setBindingSymptomHypothesisP2(splittedContent[2]);
            }

            bsht.set_names_list(l_names);
            bsht.set_p1_list(l_p1_list);
            bsht.set_p2_list(l_p2_list);
        }
    }

    public void fullyReadFileToClasses(File file,
                                       ArrayList<Hypothesis> hypothesis,
                                       ArrayList<Symptom> symptoms,
                                       ArrayList<GeneralSymptom> generalSymptoms,
                                       ArrayList<BindingsOfMetaknowledgeQuestions> bindingsOfMetawaysQuestions,
                                       BindingSymptomHypothesis bindingsOfSymptomsAndHypothesis) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        readBaseInfo(bufferedReader);
        readHypothesis(bufferedReader, hypothesis);
        readSymptoms(bufferedReader, symptoms);
        readGeneralSymptoms(bufferedReader, generalSymptoms);
        readBindingsOfMetaknowledgeQuestions(bufferedReader, bindingsOfMetawaysQuestions);
        readBindingsOfSymptomsAndHypothesis(bufferedReader, bindingsOfSymptomsAndHypothesis);
    }
}
