package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

public class GeneralSymptom implements Parcelable {
    private String m_name;
    private String m_question;
    private String m_description;
    private String m_answer = "";

    public GeneralSymptom(){}

    public GeneralSymptom(String name, String question, String description){
        m_name = name;
        m_question = question;
        m_description = description;
    }

    public GeneralSymptom(Parcel in)
    {
        m_name = in.readString();
        m_question = in.readString();
        m_description = in.readString();
    }

    public void setAnswer(String answer) { m_answer = answer;}
    public String getAnswer() { return m_answer; }

    public void setGeneralSymptomName(String p_name) { m_name = p_name; }
    public String getGeneralSymptomName() { return m_name; }

    public void setGeneralSymptomQuestion(String p_question) { m_question = p_question; }
    public String getGeneralSymptomQuestion() { return m_question; }

    public void setGeneralSymptomDescription(String p_description) { m_description = p_description; }
    public String getGeneralSymptomDescription() { return m_description; }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_name);
        dest.writeString(m_question);
        dest.writeString(m_description);
    }

    public static final Parcelable.Creator<GeneralSymptom> CREATOR = new Parcelable.Creator<GeneralSymptom>()
    {
        public GeneralSymptom createFromParcel(Parcel in)
        {
            return new GeneralSymptom(in);
        }

        public GeneralSymptom[] newArray(int size)
        {
            return new GeneralSymptom[size];
        }
    };
}
