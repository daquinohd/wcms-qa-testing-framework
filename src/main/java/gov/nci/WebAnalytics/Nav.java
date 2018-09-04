package gov.nci.WebAnalytics;

import gov.nci.Utilities.ConfigReader;

public class Nav {

	// Page navigation URLs
	public String homePage;
	public String blogPostPage;
	public String blogSeriesPage;
	public String cthpPatient;
	public String cthpHP;
	public String innerPage;
	public String landingPage;
	public String pdqPage;
	public String topicPage;
	public String spanishPage;
	public String appModulePage;
	public String basicSearchPage;
	public String advSearchPage;
	public String resultsPage;

	public Nav(String environment) {

		ConfigReader config = new ConfigReader(environment);

		this.homePage = config.getPageURL("HomePage");
		this.blogPostPage = config.getPageURL("BlogPostPage");
		this.blogSeriesPage = config.getPageURL("BlogSeriesPage");
		this.cthpPatient = config.getPageURL("CTHPPatient");
		this.cthpHP = config.getPageURL("CTHPHP");
		this.innerPage = config.getPageURL("InnerPage");
		this.landingPage = config.getPageURL("LandingPage");
		this.pdqPage = config.getPageURL("PDQPage");
		this.topicPage = config.getPageURL("TopicPage");
		this.spanishPage = config.getPageURL("SpanishPage");
		this.appModulePage = config.getPageURL("AppModulePage");
		this.basicSearchPage = config.getPageURL("BasicClinicalTrialSearchURL");
		this.advSearchPage = config.getPageURL("AdvanceSearchPageURL");
		this.resultsPage = config.getPageURL("ResultsPageURL");
	}

	// // New ConfigReader
	// private static ConfigReader config = new ConfigReader();

	// // Get our page navigation URLs	
	// public static String homePage = config.getPageURL("HomePage");
	// public static String blogPostPage = config.getPageURL("BlogPostPage");
	// public static String blogSeriesPage = config.getPageURL("BlogSeriesPage");
	// public static String cthpPatient = config.getPageURL("CTHPPatient");
	// public static String cthpHP = config.getPageURL("CTHPHP");
	// public static String innerPage = config.getPageURL("InnerPage");
	// public static String landingPage = config.getPageURL("LandingPage");
	// public static String pdqPage = config.getPageURL("PDQPage");
	// public static String topicPage = config.getPageURL("TopicPage");
	// public static String spanishPage = config.getPageURL("SpanishPage");
	// public static String appModulePage = config.getPageURL("AppModulePage");
	// public static String basicSearchPage = config.getPageURL("BasicClinicalTrialSearchURL");
	// public static String advSearchPage = config.getPageURL("AdvanceSearchPageURL");
	// public static String resultsPage = config.getPageURL("ResultsPageURL");

}