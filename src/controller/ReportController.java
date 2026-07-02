package controller;

import entity.ReportSummary;
import service.ReportService;

public class ReportController {
    private final ReportService reportService;

    public ReportController() {
        this.reportService = new ReportService();
    }

    public ReportSummary getEmployeeReport() throws Exception {
        return reportService.getEmployeeReport();
    }
}
