// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openstreetmap.osmosis.kakasi.common.JpnToEng;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;
import org.villseriol.kakasi.api.Kakasi;
import org.villseriol.kakasi.api.KakasiConfig;


public class KakasiTest extends AbstractDataTest {
    private static final Charset EUC_JP = Charset.forName("EUC-JP");

    @Before
    public void setUp() {
        KakasiConfig config = KakasiConfig.createDefaultConfig();

        File largeDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.L", EUC_JP);
        String largeDictionaryPath = largeDictionary.getAbsolutePath();

        File stationDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.station", EUC_JP);
        String stationDictionaryPath = stationDictionary.getAbsolutePath();

        List<String> dictionaries = new ArrayList<>();
        dictionaries.add(largeDictionaryPath);
        dictionaries.add(stationDictionaryPath);

        config.setDictionaries(dictionaries);
        Kakasi.configure(config);
    }


    @Before
    public void tareDown() {
        // Do nothing
    }


    @Test
    public void testSplitter() {
        JpnToEng translator = JpnToEng.getInstance();

        assertEquals("EY4180 (kabu)", translator.run("EY4180 (株)"));
        assertEquals("EY4180 (kabu)", translator.run("EY4180 (株)"));
        assertEquals("EY4180 ( kabu )", Kakasi.run("EY4180 (株)"));
    }
}
