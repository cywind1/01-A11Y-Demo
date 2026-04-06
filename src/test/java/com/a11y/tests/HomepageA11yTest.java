package com.a11y.tests;

// Axe-core imports — the accessibility scanning engine by Deque
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;

// Selenium imports — for browser automation
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// TestNG imports — for test structure and assertions
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class HomepageA11yTest {

    // WebDriver controls the Chrome browser
    WebDriver driver;

    // Runs BEFORE each test method — sets up the browser
    @BeforeMethod
    public void setup() {
        // Launch a new Chrome browser instance
        driver = new ChromeDriver();
        // Maximize window to simulate real user viewport
        driver.manage().window().maximize();
    }

    // The actual test method — checks homepage for WCAG violations
    @Test
    public void checkHomepageWCAG22AA() throws InterruptedException {

        // Navigate to the buggy practice shop
        driver.get("https://with-bugs.practicesoftwaretesting.com");

        // Wait 8 seconds for the Angular/React app to fully render
        // This site loads content dynamically, so we need to wait
        Thread.sleep(8000);

        // Print debug info to confirm the correct page loaded
        System.out.println("=== DEBUG ===");
        System.out.println("Page title: " + driver.getTitle());
        System.out.println("Page URL:   " + driver.getCurrentUrl());
        System.out.println("Running Axe scan...");

        // Run Axe accessibility scan against multiple WCAG standards:
        // wcag2a   = WCAG 2.0 Level A   (minimum)
        // wcag2aa  = WCAG 2.0 Level AA  (standard)
        // wcag21aa = WCAG 2.1 Level AA  (adds mobile/cognitive rules)
        // wcag22aa = WCAG 2.2 Level AA  (latest — adds focus/target size rules)
        Results axeResults = new AxeBuilder()
            .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21aa", "wcag22aa"))
            .analyze(driver);

        // Extract only the violations from the scan results
        List<Rule> violations = axeResults.getViolations();

        // Print total number of violations found
        System.out.println("\n========================================");
        System.out.println("WCAG 2.2 AA Violations Found: " + violations.size());
        System.out.println("========================================");

        // Loop through each violation and print details
        for (Rule violation : violations) {
            System.out.println("\n--- VIOLATION ---");
            // The WCAG rule ID e.g. "color-contrast", "image-alt"
            System.out.println("Rule ID : " + violation.getId());
            // Severity: critical, serious, moderate, minor
            System.out.println("Impact  : " + violation.getImpact());
            // What the rule checks
            System.out.println("Desc    : " + violation.getDescription());
            // How to fix it
            System.out.println("Help    : " + violation.getHelp());
        }

        // STRICT MODE — fail the test if any violations exist
        // This mirrors real CI/CD pipeline behaviour where
        // accessibility violations block a release
        org.testng.Assert.assertEquals(violations.size(), 0,
            "WCAG violations found: " + violations.size());
    }

    // Runs AFTER each test method — cleans up the browser
    @AfterMethod
    public void teardown() {
        // Close browser if it was opened — prevents memory leaks
        if (driver != null) driver.quit();
    }
}