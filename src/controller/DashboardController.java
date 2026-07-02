package controller;

import entity.DashboardSummary;
import service.DashboardService;

public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController() {
        this.dashboardService = new DashboardService();
    }

    public DashboardSummary getSummary() throws Exception {
        return dashboardService.getSummary();
    }
}
