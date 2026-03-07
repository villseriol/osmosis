// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;
import org.openstreetmap.osmosis.core.OsmosisRuntimeException;
import org.openstreetmap.osmosis.testutil.AbstractDataTest;


public class UserConfigurationLoaderTest extends AbstractDataTest {
    private UserConfigurationLoader loader = UserConfigurationLoader.getInstance();

    @Test
    public void testEmptyConfiguration() {
        File sourceFile = dataUtils.createDataFile("empty-user-config.xml");
        UserConfiguration config = loader.load(sourceFile);
        assertNotNull(config);
        List<Path> dictionaries = config.getDictionaries();
        assertNotNull(dictionaries);
        assertTrue(dictionaries.isEmpty());

        List<Replacement> replacements = config.getReplacements();
        assertNotNull(replacements);
        assertTrue(replacements.isEmpty());

        List<TagMatch> matches = config.getTagMatchs();
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }


    @Test
    public void testMalformedConfiguration() {
        File sourceFile = dataUtils.createDataFile("malformed-user-config.xml");
        assertThrows(OsmosisRuntimeException.class, () -> {
            loader.load(sourceFile);
        });
    }


    @Test
    public void testReplacementsConfiguration() {
        File sourceFile = dataUtils.createDataFile("replacements-user-config.xml");
        UserConfiguration config = loader.load(sourceFile);
        assertNotNull(config);
        List<Replacement> replacements = config.getReplacements();
        assertNotNull(replacements);
        assertFalse(replacements.isEmpty());
        assertEquals(replacements.size(), 11);
    }
}
