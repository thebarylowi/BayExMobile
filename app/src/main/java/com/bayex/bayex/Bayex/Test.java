package com.bayex.bayex.Bayex;

import java.util.ArrayList;

public class Test {
    private ArrayList<String> pa = new ArrayList<>();
    private ArrayList<ArrayList<String>> p1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> p2 = new ArrayList<>();

    public Test(){}

    public Test(ArrayList<String> pa, ArrayList<ArrayList<String>> p1, ArrayList<ArrayList<String>> p2)
    {
        this.pa = pa;
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setP1(ArrayList<ArrayList<String>> p1) {
        this.p1 = p1;
    }

    public ArrayList<ArrayList<String>> getP1List(){ return p1; }

    public void setP2 (ArrayList<ArrayList<String>> p2) {
        this.p2 = p2;
    }

    public ArrayList<ArrayList<String>> getP2List() { return p2; }

    public void setPa (ArrayList<String> pa) {
        this.pa = pa;
    }

    public ArrayList<String> getPaList() { return pa; }
}
