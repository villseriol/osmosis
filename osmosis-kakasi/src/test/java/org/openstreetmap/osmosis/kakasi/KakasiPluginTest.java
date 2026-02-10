package org.openstreetmap.osmosis.kakasi;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.openstreetmap.osmosis.core.Osmosis;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;

public class KakasiPluginTest extends AbstractDataTest {
    @Test
    public void testHospitalNode() throws IOException {
        File sourceFile = dataUtils.createDataFile("385218500.xml");
        File outputFile = dataUtils.newFile();
        File largeDictionary = dataUtils.createDataFile("dictionary/SKK-JISYO.L");

        Osmosis.run(
                new String[] {
                        "-q",
                        "--read-xml",
                        sourceFile.getPath(),
                        "--kakasi",
                        "tagRegex=(operator|ref|tunnel:name|bridge:name|name|name:en|name:ja|alt_name|brand|brand:ja)",
                        "dictPath=" + largeDictionary.getPath(),
                        "--write-xml",
                        outputFile.getPath()
                });
    }
}
