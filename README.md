# WCMS QA Testing Framework
Automated test framework using Se WebDriver, TestNG and Maven written in Java.

## Prerequisites:
- JDK 1.8.0_144 or later
- [Apache maven](http://maven.apache.org/download.cgi) tool 
- Eclipse Neon or later (optional)

## Command line execution:

To run the default test suite (all tests), execute the command 

    mvn test

### Specifying the environment

If no environment is specified, the default is QA.

To use a different environment, on the maven commmand line, specify
`-Denvironment=<environment_name>`

    Valid values are:
      acsf
      int
      stage
      test
      qa
      dev
      

### Specifying a test suite.

To execute a specific test suite, execute the command

    mvn test -Dsurefire.suiteXmlFiles=<testfile>

Where `<testfile>` is the test suite file and path.  Multiple test suites may be specified by separating them
with commas. e.g.

    mvn test -Dsurefire.suiteXmlFiles=resources\testng-CTS.xml,resources\testng-R4R.xml

### Specifying the browser

Tests default to running in Chrome Headless. To use a different browser, include the arguement -Dbrowser=<browser>

    mvn test -Dbrowser=Chrome

Valid browser names are:
* Chrome
* ChromeHeadless
* Firefox
* GeckoHeadless
* iPhone6
* iPad

**NOTE:** Names are CaSe seNstive.  (e.g. "Chrome" is not the same as "chrome.")

# Testing Web Analytics
This package will eventually be used for testing analytics exclusively. 

### When to run the test
* Any release involving analytics or DTM changes.
* Any release involving changes to site HTML. 
* Any release involving changes to CSS or JavaScript.
* Every major release.

### How to track issues
Given the nature of CancerGov's release schedule and content changes, we can expect a handful of errors when the script is run. A pass rate of over 90% generally means that analytics are working as expected, but there are a few issues that need to be resolved. Anything below that may point to a larger issue in the site's code or require an update to the test data.

Errors will generally appear as one of two flavors:

### Assertion failures
These are cases where the test runs as expected, but does not pass. 

Examples:

`BlogRightRailClick_Test.testBlogPostRailMonthClick:185->doCommonClassAssertions:222 expected [BlogArchiveDateClick] but found [BlogAccordionAction]`

`BasicSearchClick_Test.testBasicKeywordMatch:102 event39 expected [true] but found [false]`

Reporting:
1. Have the analytics team verify the error manually.
1. If the issue is verified, submit a ticket to the dev team to resolve the issue.

### DOM errors
These are cases where a test fails due to a navigation error, page timeout, null selector, or some other server error.

Examples:

`ErrorClick_Test.testErrorPageSearchEn:46->AnalyticsTestClickBase.handleTestErrors:122 Click event exception in testErrorPageSearchEn(): org.openqa.selenium.NoSuchElementException: Unable to locate element: //label[@for='englishl']`

`BlogPostClick_Test.testBlogPostRecommendedClick:89->AnalyticsTestClickBase.handleTestErrors:122 Click event exception in testBlogPostRecommendedClick(): java.lang.NullPointerException`

`TrialPrintLoad_Test.testCtsPrintPageLoad:59->AnalyticsTestLoadBase.handleTestErrors:170 Load event exception in testCtsPrintPageLoad(): org.openqa.selenium.TimeoutException: Expected condition failed: waiting for gov.nci.framework.PageObjectBase$$Lambda$333/0x000000080053d840@5bb24a84 (tried for 20 second(s) with 500 milliseconds interval)`

Reporting:
1. Have the analytics team verify the error manually.
1. If the issue is verified, the analytics team should submit a ticket to either: 1) update the code or content to pass the correct value or 2) update the test to match the site's data.

