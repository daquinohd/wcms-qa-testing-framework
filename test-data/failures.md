### Test failures: 

- Assert.assertEquals(beacon.props.get(6), analyticsMetaData.getMetaTitle()); 
  - home page load, og:title not set
  - Fix: Drupal config

- Assert.assertTrue(beacon.props.get(25).matches(REGEX_MMDDYY), "Common invalid date");
  - all page loads, dcterms.issued not set everywhere
  - Fix: Drupal config

- Assert.assertEquals(beacon.eVars.get(48), analyticsMetaData.getMetaIsPartOf() + " Viewer");
  - blog post load, prop48 not set
  - Fix: DTM library custom rule

- testBlogSeriesSubscribeClick(), testBlogPostSubscribeClick()
  - false negative

- testDictionaryPopupLoad() 
  - deprecated functionality
