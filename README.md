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

