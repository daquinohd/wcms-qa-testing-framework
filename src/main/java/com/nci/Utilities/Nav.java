package com.nci.Utilities;

import gov.nci.Utilities.ConfigReader;

public class WANav {

	// New ConfigReader
	private static ConfigReader config = new ConfigReader();

	// Get our page navigation URLs	
	public static String homePage = config.getPageURL("HomePage");
	public static String blogPostPage = config.getPageURL("BlogPostPage");
	public static String blogSeriesPage = config.getPageURL("BlogSeriesPage");
	public static String cthpPatient = config.getPageURL("CTHPPatient");
	public static String cthpHP = config.getPageURL("CTHPHP");
	public static String innerPage = config.getPageURL("InnerPage");
	public static String landingPage = config.getPageURL("LandingPage");
	public static String pdqPage = config.getPageURL("PDQPage");
	public static String topicPage = config.getPageURL("TopicPage");
	public static String spanishPage = config.getPageURL("SpanishPage");
	public static String appModulePage = config.getPageURL("AppModulePage");
	public static String basicSearchPage = config.getPageURL("BasicClinicalTrialSearchURL");
	public static String advSearchPage = config.getPageURL("AdvanceSearchPageURL");
	public static String resultsPage = config.getPageURL("ResultsPageURL");

}