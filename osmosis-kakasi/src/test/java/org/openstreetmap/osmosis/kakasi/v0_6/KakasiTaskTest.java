// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import java.io.File;

import org.junit.Test;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class KakasiTaskTest extends AbstractDataTest {

    @Test
    public void testLoadConfiguration() {
        File config = dataUtils.createDataFile("v0_6/empty-user-config.xml");
        KakasiTask task = new KakasiTask(config.getPath());
    }
}
