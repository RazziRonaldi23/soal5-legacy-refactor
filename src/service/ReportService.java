package service;

import entity.ReportSummary;
import repository.ReportRepository;

public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService() {
        this.reportRepository = new ReportRepository();
    }

    public ReportSummary getEmployeeReport() throws Exception {
        return reportRepository.getEmployeeReport();
    }
}
