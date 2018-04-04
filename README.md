# WCMS QA Testing Framework
Automated test framework using Se WebDriver, TestNG and Maven written in Java.

## Prerequisites on testing machine:
- JDK 1.8.0_144 or later
- Eclipse Neon or later

## Installation steps
1. Open Eclipse and workspace.
2. Install TestNG
   1. Launch the Eclipse IDE and from Help menu, click **Install New Software**.
   2. Click Add button.
   3. Enter name as **TestNG** and type http://beust.com/eclipse/ as location. Click OK.
   4. The TestNG option should display in the available software list. Click TestNG and click Next button.
   5. Accept the terms of the license agreement, then click Finish.
   6. Click Next again on the succeeding dialog box until it prompts you to Restart the Eclipse and click Yes.
3. Wait for installation to complete, then restart Eclipse.

## To run tests:
1. In Eclipse navigator, expand resources -> drivers.
2. Right-click **testng.xml**, select **Run as -> TestNG Suite**.

## To change tests or browsers: 
1. In Eclipse navigator, expand resources -> drivers.
2. Open **testng.xml** for editing. 
3. Add or uncomment any elements that should be tested.

## To change testing tier:
1. In Eclipse navigator, expand src/main/java/com.nci.Utilities.
2. Open **ConfigReader.java** for editing.
3. Change the "/configuration/ConfigXXXX.property" string to match the desired tier.
4. Save and run tests.

## Installing Drivers/Plugins
*Note - the required drivers are currently pulled in with the Git repo and do not need to be installed. If any of these need to be reinstalled manually, download from the given link, extract, then copy the files to the referenced path in Eclipse.*
### Maven Install:
1. Go to URL: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-eclipse-plugin/2.10/
2. Click on maven-eclipse-plugin-2.10-source-release.zip
3. Extract files in the appropriate folder
### Selenium Webdriver:
1. http://selenium-release.storage.googleapis.com/index.html?path=3.5/
2. Click on  selenium-java-3.5.0.zip
3. Extract files in the appropriate folder
### ChromeDriver:
1. https://chromedriver.storage.googleapis.com/index.html?path=2.34/
2. Click on chromedriver_win32.zip
3. Extract files in the appropriate folder
### Gecko Driver:
1. https://github.com/mozilla/geckodriver/releases
2. Click on geckodriver-v0.18.0-win64.zip
3. Extract files in the appropriate folder
### Apache Maven:
1. https://maven.apache.org/download.cgi
2. Click on apache-maven-3.5.2-bin.zip
3. Extract files in the appropriate folder
