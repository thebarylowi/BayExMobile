package com.bayex.bayex.Bayex;

import android.os.Parcel;
import android.os.Parcelable;

public class Answers implements Parcelable {
    String m_question;
    int m_answer;

    public Answers(){}

    public Answers(String question, int answer)
    {
        m_question = question;
        m_answer = answer;
    }

    public Answers(Parcel in){
        m_question = in.readString();
        m_answer = in.readInt();
    }

    public void setQuestion(String question) { m_question = question; }
    public String getQuestion() { return m_question; }

    public void setAnswer(int answer) { m_answer = answer; }
    public int getAnswer() { return m_answer; }



    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_question);
        dest.writeInt(m_answer);
    }

    public static final Parcelable.Creator<Answers> CREATOR = new Parcelable.Creator<Answers>()
    {
        public Answers createFromParcel(Parcel in)
        {
            return new Answers(in);
        }

        public Answers[] newArray(int size)
        {
            return new Answers[size];
        }
    };
}
