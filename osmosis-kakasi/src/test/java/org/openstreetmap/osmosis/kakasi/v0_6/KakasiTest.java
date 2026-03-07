// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openstreetmap.osmosis.kakasi.common.KakasiPipeline;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class KakasiTest extends AbstractDataTest {
    private static final KakasiPipeline TRANSLATOR = KakasiPipeline.getInstance();

    @BeforeClass
    public static void onlyOnce() {
        TRANSLATOR.init();
    }


    @Before
    public void tareDown() {
        // Do nothing
    }


    @Test
    public void testSplitter() {
        assertEquals("EY4180 (kabu)", TRANSLATOR.run("EY4180 (株)"));
        assertEquals("EY4180 (kabu)", TRANSLATOR.run("EY4180 (株)"));
        assertEquals("EY4180 )((kabu())", TRANSLATOR.run("EY4180 )((株())"));
        assertEquals("EY>4180 )((kabu())", TRANSLATOR.run("EY>4180 )((株())"));
    }
}
