// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Test;
import org.openstreetmap.osmosis.core.Osmosis;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class KakasiPluginTest extends AbstractDataTest {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Test
    public void testHospitalNode() throws IOException {
        File sourceFile = dataUtils.createDataFile("385218500.xml");
        File outputFile = dataUtils.newFile();

        File configFile = dataUtils.createDataFile("standard-user-config.xml");

        // @formatter:off
        Osmosis.run(new String[] {
            "-q",
            "--read-xml",
            sourceFile.getPath(),
            "--kakasi",
            "configFile=" + configFile,
            "--write-xml", outputFile.getPath() });
        // @formatter:on
    }
}
