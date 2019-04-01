package com.bayex.bayex.Bayex;

public class KnowledgeBase {

    private static Double m_number;
    private static Double m_version;
    private static String m_description;

    public KnowledgeBase() {}

    public static void setBaseNumber(Double p_number){ m_number = p_number; }
    public static Double getBaseNumber() { return m_number; }

    public static void setBaseVersion(Double p_version) { m_version = p_version; }
    public static Double getBaseVersion() { return m_version; }

    public static void setDecription(String p_description) { m_description = p_description; }
    public static String getDescription() { return m_description; }
}
