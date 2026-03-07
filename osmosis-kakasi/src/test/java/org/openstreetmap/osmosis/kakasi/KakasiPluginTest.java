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
        File sourceFile = dataUtils.createDataFile("v0_6/385218500.xml");
        File outputFile = dataUtils.newFile();

        File configFile = dataUtils.createDataFile("v0_6/standard-user-config.xml");

        // @formatter:off
        Osmosis.run(new String[] {
            "-q",
            "--read-xml",
            sourceFile.getPath(),
            "--kakasi",
            "file=" + configFile,
            "--write-xml", outputFile.getPath() });
        // @formatter:on
    }
}
