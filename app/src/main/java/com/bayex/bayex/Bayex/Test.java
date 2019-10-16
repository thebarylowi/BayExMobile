package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.math.BigDecimal;

public class Test implements Parcelable {
    private ArrayList<BigDecimal> pa = new ArrayList<>();
    private ArrayList<BigDecimal> result = new ArrayList<>();

    private ArrayList<BigDecimal> p1 = new ArrayList<>();
    private ArrayList<BigDecimal> p2 = new ArrayList<>();
    private ArrayList<String> hipotesisName = new ArrayList<>();

    public int describeContents() {
        return 0;
    }

    public Test(){}

    public Test(Parcel in){
        pa = in.readArrayList(pa.getClass().getClassLoader());
        result = in.readArrayList(result.getClass().getClassLoader());
        p1 = in.readArrayList(p1.getClass().getClassLoader());
        p2 = in.readArrayList(p2.getClass().getClassLoader());
        hipotesisName = in.readArrayList(hipotesisName.getClass().getClassLoader());
    }

    public void setHM(ArrayList<String> p_name)
    {
        hipotesisName = p_name;
    }

    public ArrayList<String> getHN()
    {
        return hipotesisName;
    }

    public void setP1(ArrayList<String> p1) {
        for(int i = 0; i < p1.size(); i++)
        {
            this.p1.add(new BigDecimal(p1.get(i)));
        }
    }

    public ArrayList<BigDecimal> getP1List(){ return p1; }

    public void setP2 (ArrayList<String> p2) {
        for(int i = 0; i < p2.size(); i++)
        {
            this.p2.add(new BigDecimal(p2.get(i)));
        }
    }

    public ArrayList<BigDecimal> getP2List() { return p2; }

    public void setSPa(String p_a)
    {
        pa.add(new BigDecimal(p_a));
    }

    public void setResult(int index, String p)
    {
            this.pa.set(index, new BigDecimal(p));
    }

    public ArrayList<BigDecimal> getResult()
    {
        return  result;
    }

    public void setPa (ArrayList<String> pa) {

        for(int i = 0; i < pa.size(); i++)
        {
            this.pa.add(new BigDecimal(pa.get(i)));
        }
    }

    public ArrayList<BigDecimal> getPaList() { return pa; }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(pa);
        dest.writeList(result);
        dest.writeList(p1);
        dest.writeList(p2);
        dest.writeList(hipotesisName);
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>()
    {
        public Test createFromParcel(Parcel in)
        {
            return new Test(in);
        }

        public Test[] newArray(int size)
        {
            return new Test[size];
        }
    };

}
