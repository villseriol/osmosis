// This software is released into the Public Domain.  See copying.txt for details.
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

        File largeDictionaryFile = dataUtils.createDataFile("dictionary/SKK-JISYO.L");

        // @formatter:off
        Osmosis.run(new String[] {
            "-q",
            "--read-xml",
            sourceFile.getPath(),
            "--kakasi",
            "tagRegex=(operator|ref|tunnel:name|bridge:name|name|name:en|name:ja|alt_name|brand|brand:ja)",
            "dictPaths=" + largeDictionaryFile.getAbsolutePath(),
            "--write-xml", outputFile.getPath() });
        // @formatter:on
    }
}
