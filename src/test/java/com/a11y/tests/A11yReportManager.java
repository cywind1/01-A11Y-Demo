package com.a11y.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class A11yReportManager {

    // Report file path — configurable per test
    private String reportPath;
    private String testName;
    private String testDescription;
    private List<String[]> steps = new ArrayList<>();
    private String startTime;

    // Update: Environment URL — set automatically from browser after page loads
    private String environment = "-";

    // Default constructor — saves to homepage report
    public A11yReportManager() {
        this("target/a11y-report.html");
    }

    // Custom constructor — saves to specified path
    public A11yReportManager(String reportPath) {
        this.reportPath = reportPath;
        this.startTime = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss"));
    }

    // Update:Called after driver.get() to capture the actual URL from browser
    public void setEnvironment(String url) {
        this.environment = url;
    }

    public void startTest(String name, String description) {
        this.testName = name;
        this.testDescription = description;
    }

    public void info(String message) {
        steps.add(new String[]{"INFO", message});
    }

    public void fail(String message) {
        steps.add(new String[]{"FAIL", message});
    }

    public void pass(String message) {
        steps.add(new String[]{"PASS", message});
    }

    public void saveReport(int violationCount) {
        StringBuilder rows = new StringBuilder();
        for (String[] step : steps) {
            String status = step[0];
            String msg = step[1];
            String badgeColor = status.equals("FAIL") ? "#e05252"
                : status.equals("PASS") ? "#52a852" : "#5b8fd4";
            rows.append("<tr>")
                .append("<td><span style='background:").append(badgeColor)
                .append(";color:#fff;padding:2px 8px;border-radius:4px;font-size:12px;'>")
                .append(status).append("</span></td>")
                .append("<td>").append(msg).append("</td>")
                .append("</tr>");
        }

        String statusColor = violationCount > 0 ? "#e05252" : "#52a852";
        String statusText = violationCount > 0
            ? "FAIL — " + violationCount + " violations found"
            : "PASS — No violations found";

        String html = "<!DOCTYPE html><html><head>"
            + "<meta charset='UTF-8'>"
            + "<title>WCAG 2.2 AA Accessibility Report</title>"
            + "<style>"
            + "body{font-family:Arial,sans-serif;background:#1a1a2e;color:#e0e0e0;margin:0;padding:20px;}"
            + "h1{color:#fff;border-bottom:2px solid #5b8fd4;padding-bottom:10px;}"
            + "h2{color:#ccc;font-size:16px;}"
            + ".badge{display:inline-block;padding:4px 12px;border-radius:4px;color:#fff;font-weight:bold;}"
            + ".info-box{background:#16213e;border-radius:8px;padding:16px;margin:16px 0;}"
            + ".info-box table{width:100%;border-collapse:collapse;}"
            + ".info-box td{padding:6px 12px;border-bottom:1px solid #2a2a4a;}"
            + ".info-box td:first-child{color:#aaa;width:180px;}"
            + "table.results{width:100%;border-collapse:collapse;background:#16213e;border-radius:8px;overflow:hidden;}"
            + "table.results th{background:#0f3460;padding:10px 14px;text-align:left;color:#fff;}"
            + "table.results td{padding:10px 14px;border-bottom:1px solid #2a2a4a;vertical-align:top;}"
            + "table.results tr:last-child td{border-bottom:none;}"
            + "</style></head><body>"
            + "<h1>WCAG 2.2 AA Accessibility Report</h1>"
            + "<div class='info-box'><table>"
            + "<tr><td>Report name</td><td>" + testName + "</td></tr>"
            + "<tr><td>Date</td><td>" + startTime + "</td></tr>"
            + "<tr><td>Tester</td><td>cywind1</td></tr>"
            + "<tr><td>Environment</td><td>" + environment + "</td></tr>"
            + "<tr><td>WCAG Standard</td><td>2.2 Level AA</td></tr>"
            + "<tr><td>Browser</td><td>Google Chrome</td></tr>"
            + "<tr><td>Tool</td><td>Axe-core by Deque</td></tr>"
            + "<tr><td>Overall result</td><td>"
            + "<span class='badge' style='background:" + statusColor + ";'>"
            + statusText + "</span></td></tr>"
            + "</table></div>"
            + "<h2>" + testName + " — " + testDescription + "</h2>"
            + "<table class='results'>"
            + "<thead><tr><th style='width:80px'>Status</th><th>Details</th></tr></thead>"
            + "<tbody>" + rows + "</tbody>"
            + "</table>"
            + "</body></html>";

        try {
            new java.io.File("target").mkdirs();
            FileWriter writer = new FileWriter(reportPath);
            writer.write(html);
            writer.close();
            System.out.println("\n✅ Report saved to: " + reportPath);
        } catch (IOException e) {
            System.err.println("Failed to write report: " + e.getMessage());
        }
    }
}