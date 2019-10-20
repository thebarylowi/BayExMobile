package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

public class Symptom implements Parcelable {
    private String m_name;
    private String m_question;
    private String m_description;
    private boolean m_isAnswered = false;

    public Symptom(String name, String question, String description)
    {
        m_name = name;
        m_question = question;
        m_description = description;
    }

    public boolean isAnswered()
    {
        return m_isAnswered;
    }

    public void setIsAnswered(boolean p_flag)
    {
        m_isAnswered = p_flag;
    }

    public Symptom(Parcel in)
    {
        m_name = in.readString();
        m_question = in.readString();
        m_description = in.readString();
        m_isAnswered = in.readInt() == 1;
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
        dest.writeInt(m_isAnswered ? 1 : 0);
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
