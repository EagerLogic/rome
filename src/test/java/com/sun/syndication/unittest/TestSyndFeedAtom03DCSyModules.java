/*
 * Created on Jun 25, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.sun.syndication.unittest;

import java.util.Date;
import java.util.List;

import com.sun.syndication.feed.module.DCModule;
import com.sun.syndication.feed.module.SyModule;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.impl.DateParser;

/**
 * @author pat
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestSyndFeedAtom03DCSyModules extends TestSyndFeedAtom03 {

    public TestSyndFeedAtom03DCSyModules() {
        super("atom_0.3", "atom_0.3_DC_Sy.xml");
    }

    protected TestSyndFeedAtom03DCSyModules(final String type) {
        super(type);
    }

    protected TestSyndFeedAtom03DCSyModules(final String feedType, final String feedFileName) {
        super(feedType, feedFileName);
    }

    public void testModules() throws Exception {
        final DCModule dc = (DCModule) this.getCachedSyndFeed().getModule(DCModule.URI);
        assertNotNull(dc);
        final SyModule sy = (SyModule) this.getCachedSyndFeed().getModule(SyModule.URI);
        assertNotNull(sy);
    }

    public void testFeedDCModule() throws Exception {
        final DCModule dc = (DCModule) this.getCachedSyndFeed().getModule(DCModule.URI);
        _testDCModule(dc, "feed.", false, 0);
    }

    protected void _testDCModule(final DCModule dc, final String prefix, final boolean isEntry, final int index) throws Exception {
        assertNotNull(dc);
        assertProperty(dc.getTitle(), prefix + "dc:title");

        assertProperty(dc.getCreator(), prefix + "dc:creator"); // Convenience
        // method

        assertProperty(dc.getSubjects().get(0).getValue(), prefix + "dc:subject[0]");
        final String taxo0 = dc.getSubjects().get(0).getTaxonomyUri();
        if (taxo0 != null) {
            assertProperty(taxo0, prefix + "dc:subject[0].taxo:topic^resource");
        }
        assertProperty(dc.getSubjects().get(1).getValue(), prefix + "dc:subject[1]");
        final String taxo1 = dc.getSubjects().get(1).getTaxonomyUri();
        if (taxo1 != null) {
            assertProperty(taxo1, prefix + "dc:subject[1].taxo:topic^resource");
        }
        assertProperty(dc.getDescription(), prefix + "dc:description");
        assertProperty(dc.getPublisher(), prefix + "dc:publisher");
        assertProperty(dc.getContributors().get(0), prefix + "dc:contributor[0]");
        assertProperty(dc.getContributors().get(1), prefix + "dc:contributor[1]");
        final Date date = DateParser.parseW3CDateTime("2000-0" + (index + 1) + "-01T00:00:00Z");
        assertEquals(dc.getDate(), date);
        assertProperty(dc.getType(), prefix + "dc:type");
        assertProperty(dc.getFormat(), prefix + "dc:format");
        assertProperty(dc.getIdentifier(), prefix + "dc:identifier");
        assertProperty(dc.getSource(), prefix + "dc:source");
        assertProperty(dc.getLanguage(), prefix + "dc:language");
        assertProperty(dc.getRelation(), prefix + "dc:relation");
        assertProperty(dc.getCoverage(), prefix + "dc:coverage");

        if (isEntry) {
            assertProperty(dc.getRights(), prefix + "dc:rights");
        } else {
            assertProperty(dc.getRights(), prefix + "copyright"); // in
                                                                  // header
            // is
            // convenience
            // method
        }
    }

    public void testFeedSyModule() throws Exception {
        final SyModule sy = (SyModule) this.getCachedSyndFeed().getModule(SyModule.URI);
        assertNotNull(sy);
        assertEquals(sy.getUpdatePeriod(), SyModule.HOURLY);
        assertEquals(sy.getUpdateFrequency(), 100);
        final Date date = DateParser.parseW3CDateTime("2001-01-01T01:00+00:00");
        assertEquals(sy.getUpdateBase(), date);
    }

    public void testEntriesDCModule() throws Exception {
        _testEntryDCModule(0);
        _testEntryDCModule(1);
    }

    protected void _testEntryDCModule(final int i) throws Exception {
        final List<SyndEntry> entries = this.getCachedSyndFeed().getEntries();
        final SyndEntry entry = entries.get(i);
        final DCModule dc = (DCModule) entry.getModule(DCModule.URI);
        _testDCModule(dc, "feed.entry[" + i + "].", true, i);

    }

    @Override
    public void testLanguage() throws Exception {
        assertEqualsStr("feed.dc:language", this.getCachedSyndFeed().getLanguage());
    }
}