package com.bayex.bayex.Bayex;

public class BindingsOfMetaknowledgeQuestions {
    private int m_number;
    private char m_type;

    public BindingsOfMetaknowledgeQuestions(int number, char type)
    {
        m_number = number;
        m_type = type;
    }

    public void setBindingOfMetawaysQuestionsNumber(int p_number) { m_number = p_number; }
    public int getBindingOfMetawaysQuestionsNumber() { return m_number; }

    public void setBindingOfMetawaysQuestionsType(char p_type) { m_type = p_type; }
    public char getBindingOfMetawaysQuestionsType() { return m_type; }
}
