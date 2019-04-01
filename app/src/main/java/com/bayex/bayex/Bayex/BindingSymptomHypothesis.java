package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BindingSymptomHypothesis implements Parcelable {
    private ArrayList<String> m_number = new ArrayList<>();
    private ArrayList<String> p1 = new ArrayList<>();
    private ArrayList<String> p2 = new ArrayList<>();

    public BindingSymptomHypothesis(){}
    public BindingSymptomHypothesis(ArrayList<String> number, ArrayList<String> p_p1, ArrayList<String> p_p2)
    {
        m_number = number;
        p1 = p_p1;
        p2 = p_p2;
    }

    public BindingSymptomHypothesis(Parcel in)
    {
        in.readStringList(m_number);
        in.readStringList(p1);
        in.readStringList(p2);
    }

    public void setBindingSymptomHypothesisNumber(String p_number) { m_number.add(p_number); }
    public ArrayList<String> getBindingSymptomHypothesisNumber() { return m_number; }

    public void setBindingSymptomHypothesisP1(String p_value) { p1.add(p_value); }
    public ArrayList<String> getBindingSymptomHypothesisP1() { return p1;}

    public void setBindingSymptomHypothesisP2(String p_value) { p2.add(p_value); }
    public ArrayList<String> getBindingSymptomHypothesisP2() { return p2;}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(m_number);
        dest.writeStringList(p1);
        dest.writeStringList(p2);
    }

    public static final Parcelable.Creator<BindingSymptomHypothesis> CREATOR = new Parcelable.Creator<BindingSymptomHypothesis>()
    {
        public BindingSymptomHypothesis createFromParcel(Parcel in)
        {
            return new BindingSymptomHypothesis(in);
        }

        public BindingSymptomHypothesis[] newArray(int size)
        {
            return new BindingSymptomHypothesis[size];
        }
    };
}
