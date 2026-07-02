package service;

import entity.DashboardSummary;
import repository.DashboardRepository;

public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public DashboardService() {
        this.dashboardRepository = new DashboardRepository();
    }

    public DashboardSummary getSummary() throws Exception {
        return dashboardRepository.getSummary();
    }
}
