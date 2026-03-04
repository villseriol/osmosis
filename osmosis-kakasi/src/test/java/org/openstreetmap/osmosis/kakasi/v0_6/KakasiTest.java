// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.osmosis.kakasi.common.JpnToEng;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class KakasiTest extends AbstractDataTest {
    private static final Charset EUC_JP = Charset.forName("EUC-JP");
    private static final JpnToEng TRANSLATOR = JpnToEng.getInstance();

    @Before
    public void setUp() {
        File largeDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.L", EUC_JP);
        String largeDictionaryPath = largeDictionary.getAbsolutePath();

        File stationDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.station", EUC_JP);
        String stationDictionaryPath = stationDictionary.getAbsolutePath();

        TRANSLATOR.addDictionaryPath(largeDictionaryPath);
        TRANSLATOR.addDictionaryPath(stationDictionaryPath);

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
