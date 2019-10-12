package com.bayex.bayex.Bayex;

import java.util.ArrayList;
import java.math.BigDecimal;

public class Test {
    private ArrayList<BigDecimal> pa = new ArrayList<>();
    private ArrayList<BigDecimal> result = new ArrayList<>();

    private ArrayList<BigDecimal> p1 = new ArrayList<>();
    private ArrayList<BigDecimal> p2 = new ArrayList<>();
    private ArrayList<String> hipotesisName = new ArrayList<>();

    public Test(){}

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

    public void setBPa(int index, BigDecimal p)
    {
        if(result.size() < pa.size())
        {
            this.result.add(p);
        }
        else {
            this.result.set(index, p);
        }
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

}
