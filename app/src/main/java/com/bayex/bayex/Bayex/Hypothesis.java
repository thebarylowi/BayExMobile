package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

public class Hypothesis implements Parcelable {
    private String m_name;
    private String m_description;
    private String m_aprioriProbability;

    public Hypothesis(){}

    public Hypothesis(String name, String description, String probability)
    {
        m_name = name;
        m_description = description;
        m_aprioriProbability = probability;
    }

    public Hypothesis(Parcel in){
        m_name = in.readString();
        m_description = in.readString();
        m_aprioriProbability = in.readString();
    }

    public void setHypothesisName(String p_name) { m_name = p_name; }
    public String getHypothesisName() { return m_name; }

    public void setHypothesisDescription(String p_description) { m_description = p_description; }
    public String getHypothesisDescription() {return m_description; }

    public void setHypothesisProbability(String p_probability) { m_aprioriProbability = p_probability; }
    public String getHypothseisProbability() { return m_aprioriProbability; }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_name);
        dest.writeString(m_description);
        dest.writeString(m_aprioriProbability);
    }

    public static final Parcelable.Creator<Hypothesis> CREATOR = new Parcelable.Creator<Hypothesis>()
    {
        public Hypothesis createFromParcel(Parcel in)
        {
            return new Hypothesis(in);
        }

        public Hypothesis[] newArray(int size)
        {
            return new Hypothesis[size];
        }
    };
}
