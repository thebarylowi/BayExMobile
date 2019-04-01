package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

public class Symptom implements Parcelable {
    private String m_name;
    private String m_question;
    private String m_description;

    public Symptom(){}

    public Symptom(String name, String question, String description)
    {
        m_name = name;
        m_question = question;
        m_description = description;
    }

    public Symptom(Parcel in)
    {
        m_name = in.readString();
        m_question = in.readString();
        m_description = in.readString();
    }

    public void setSymptomName(String p_name) { m_name = p_name; }
    public String getSymptomName() { return m_name; }

    public void setQuestion(String p_question) { m_question = p_question; }
    public String getQuestion() { return m_question; }

    public void setSymptomDescription(String p_description) { m_description = p_description; }
    public String getSymptomDescription() { return m_description; }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_name);
        dest.writeString(m_question);
        dest.writeString(m_description);
    }

    public static final Parcelable.Creator<Symptom> CREATOR = new Parcelable.Creator<Symptom>()
    {
        public Symptom createFromParcel(Parcel in)
        {
            return new Symptom(in);
        }

        public Symptom[] newArray(int size)
        {
            return new Symptom[size];
        }
    };
}
