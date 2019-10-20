package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BindingSymptomHypothesis implements Parcelable {
    private ArrayList<String> m_number = new ArrayList<>(); // lista nazw hipotez -> index od 0 - n odpowiada koljenosci wczytania pytan!!
    private ArrayList<String> p1 = new ArrayList<>();
    private ArrayList<String> p2 = new ArrayList<>();

    private ArrayList<ArrayList<String>> names = new ArrayList<>();
    private ArrayList<ArrayList<String>> l_p1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> l_p2 = new ArrayList<>();
    private ArrayList<String> m_symptomName = new ArrayList<>();


    public BindingSymptomHypothesis(){}

    public BindingSymptomHypothesis(Parcel in)
    {
        in.readStringList(m_number);
        in.readStringList(p1);
        in.readStringList(p2);

        in.readList(names, ArrayList.class.getClassLoader());
        in.readList(l_p1, ArrayList.class.getClassLoader());
        in.readList(l_p2, ArrayList.class.getClassLoader());
        in.readList(m_symptomName, ArrayList.class.getClassLoader());
    }

    public void setP2s(ArrayList<ArrayList<String>> p2)
    {
        this.l_p2 = p2;
    }

    public void setP1s(ArrayList<ArrayList<String>> p1)
    {
        this.l_p1 = p1;
    }

    public void set_symptom_name(String p_name)
    {
        m_symptomName.add(p_name);
    }

    public ArrayList<String> getSymptomNames ()
    {
        return m_symptomName;
    }

    public void set_p2_list(ArrayList<String> p_p2List)
    {
        this.l_p2.add(p_p2List);
    }

    public ArrayList<ArrayList<String>> get_p2_list() {
        return l_p2;
    }

    public void set_p1_list(ArrayList<String> p_p1List)
    {
        this.l_p1.add(p_p1List);
    }

    public ArrayList<ArrayList<String>> get_p1_list() {
        return l_p1;
    }

    public void set_names_list(ArrayList<String> p_list)
    {
        this.names.add(p_list);
    }

    public ArrayList<ArrayList<String>> get_names_list()
    {
            return names;
    }

    public void setNames(ArrayList<ArrayList<String>> names)
    {
        this.names = names;
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

        dest.writeList(names);
        dest.writeList(l_p1);
        dest.writeList(l_p2);
        dest.writeList(m_symptomName);
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
