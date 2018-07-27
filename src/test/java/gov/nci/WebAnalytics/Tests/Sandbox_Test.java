package gov.nci.WebAnalytics.Tests;

import org.testng.annotations.Test;
import org.testng.Assert;

public class Sandbox_Test extends AnalyticsTestBase {

	/// Temporary methods to verify that my new changes are picked up
	@Test(groups = { "Analytics" })
	public void testString() {
		String str = "sandboxy";
		Assert.assertNotEquals("sandbox", str);
	}
	
	@Test(groups = { "Analytics" })
	public void testInt() {
		int j = 1;
		Assert.assertTrue(j + 1 == 2);
	}

	@Test(groups = { "Analytics" })
	public void testInt2() {
		int k = 1;
		Assert.assertNotEquals(k,  2);
	}
	
	@Test(groups = { "Analytics" })
	public void testRsString() {
		String str = "clickEvent";
		Assert.assertEquals("clickEvent", str);
	}

	@Test(groups = { "Analytics" })
	public void testBool() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
	}
	
}
