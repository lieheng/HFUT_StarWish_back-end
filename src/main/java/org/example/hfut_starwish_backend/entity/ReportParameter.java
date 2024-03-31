package org.example.hfut_starwish_backend.entity;

public class ReportParameter{
    private String parameterName;
    private String parameterVal;

    public ReportParameter() {
    }

    public ReportParameter(String parameterName, String parameterVal) {
        this.parameterName = parameterName;
        this.parameterVal = parameterVal;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterVal() {
        return parameterVal;
    }

    public void setParameterVal(String parameterVal) {
        this.parameterVal = parameterVal;
    }
}