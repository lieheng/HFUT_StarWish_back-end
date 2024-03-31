package org.example.hfut_starwish_backend.entity;

public class Report {
    private String reportName;
    private ReportParameter[] reportParameters;


    public Report() {
    }

    public Report(String reportName, ReportParameter[] reportParameters) {
        this.reportName = reportName;
        this.reportParameters = reportParameters;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public ReportParameter[] getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(ReportParameter[] reportParameters) {
        this.reportParameters = reportParameters;
    }
}

