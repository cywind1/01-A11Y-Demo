# 01-A11Y-Demo — Web Accessibility Test Framework

A portfolio project demonstrating automated accessibility testing skills using
industry-standard tools. 

Built to showcase practical knowledge of WCAG 2.2 standards, Selenium WebDriver, and the Axe-core accessibility engine <br> — directly aligned with QA Engineer / Accessibility Tester roles.

---

## 🎯 Project Goal

Demonstrate the ability to:
- Automate accessibility audits using **Selenium + Java**
- Detect real **WCAG 2.2 Level AA** violations on a live e-commerce website
- Integrate **Axe-core by Deque** — the industry standard accessibility engine
- Structure tests using **TestNG** with strict pass/fail assertions
- Produce actionable violation reports suitable for developer handoff

---

## 🌐 Target Website

Practice Software Testing — Toolshop (with bugs): <br>**[https://with-bugs.practicesoftwaretesting.com](https://with-bugs.practicesoftwaretesting.com)**

A modern Angular-based e-commerce demo application intentionally built with 
accessibility defects — simulating real-world client scenarios.

---

## 🛠️ Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 21 LTS | Core programming language |
| Selenium WebDriver | 4.18.1 | Browser automation |
| Axe-core (Deque) | 4.9.1 | WCAG accessibility scanning engine |
| TestNG | 7.9.0 | Test runner and assertions |
| Maven | 3.9.x | Dependency management and build tool |
| Google Chrome | Latest | Browser under test |

---

## ♿ Accessibility Standards Covered

This framework tests against the following WCAG levels simultaneously:

| Tag | Standard | Description |
|---|---|---|
| `wcag2a` | WCAG 2.0 Level A | Minimum accessibility requirements |
| `wcag2aa` | WCAG 2.0 Level AA | Standard compliance level |
| `wcag21aa` | WCAG 2.1 Level AA | Adds mobile and cognitive rules |
| `wcag22aa` | WCAG 2.2 Level AA | Latest — adds focus and target size rules |

---

## 📁 Project Structure
```
01-A11Y-Demo/
├── src/
│   └── test/
│       └── java/
│           └── com/a11y/tests/
│               ├── HomepageA11yTest.java   ← Main accessibility test
│               └── AppTest.java            ← Sanity check
├── pom.xml                                 ← Maven dependencies
└── README.md
```
---

## 🚀 How to Run

### Prerequisites
- Java 21 LTS installed
- Maven 3.9+ installed
- Google Chrome installed

### Run the accessibility test
```bash
mvn clean test -Dtest=HomepageA11yTest
```

---

## 🔍 Violations Detected

Running the test against the target site reveals **5 real WCAG violations**:

| Rule ID | Impact | Description |
|---|---|---|
| `color-contrast` | 🟠 Serious | Text does not meet minimum contrast ratio (WCAG 1.4.3) |
| `image-alt` | 🔴 Critical | Images missing alternative text (WCAG 1.1.1) |
| `label` | 🔴 Critical | Form elements missing accessible labels (WCAG 1.3.1) |
| `list` | 🟠 Serious | Incorrect list structure in HTML (WCAG 1.3.1) |
| `select-name` | 🔴 Critical | Dropdown has no accessible name (WCAG 4.1.2) |

---
