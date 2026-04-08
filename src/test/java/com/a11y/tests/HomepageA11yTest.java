package com.a11y.tests;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class HomepageA11yTest {

    WebDriver driver;
    A11yReportManager report;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        report = new A11yReportManager();
    }

    @Test
    public void checkHomepageWCAG22AA() throws InterruptedException {

        report.startTest(
            "Homepage WCAG 2.2 AA Scan",
            "Automated accessibility scan of the Toolshop homepage"
        );

        driver.get("https://with-bugs.practicesoftwaretesting.com");
        report.info("Navigated to: " + driver.getCurrentUrl());

        Thread.sleep(8000);
        report.info("Page loaded: " + driver.getTitle());

        Results axeResults = new AxeBuilder()
            .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21aa", "wcag22aa"))
            .analyze(driver);

        List<Rule> violations = axeResults.getViolations();
        report.info("Total WCAG violations found: " + violations.size());

        for (Rule violation : violations) {
            String detail = "<b>Rule ID:</b> " + violation.getId()
                + " &nbsp;|&nbsp; <b>Impact:</b> " + violation.getImpact()
                + "<br><b>Desc:</b> " + violation.getDescription()
                + "<br><b>Help:</b> " + violation.getHelp();

            System.out.println("\n--- VIOLATION ---");
            System.out.println("Rule ID : " + violation.getId());
            System.out.println("Impact  : " + violation.getImpact());
            System.out.println("Desc    : " + violation.getDescription());
            System.out.println("Help    : " + violation.getHelp());

            report.fail(detail);
        }

        if (violations.size() > 0) {
            report.fail("WCAG 2.2 AA violations found: " + violations.size());
        } else {
            report.pass("No WCAG 2.2 AA violations found!");
        }

        // Save report before assertion
        report.saveReport(violations.size());

        // Strict mode — fails the build if violations exist
        org.testng.Assert.assertEquals(violations.size(), 0,
            "WCAG violations found: " + violations.size());
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) driver.quit();
    }
}
