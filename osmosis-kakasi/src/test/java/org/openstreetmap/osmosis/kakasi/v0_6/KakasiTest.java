package org.openstreetmap.osmosis.kakasi.v0_6;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class KakasiTest {
    @Before
    public void setUp() {
        Kakasi.configure(KakasiConfig.DEFAULT_CONFIG);
    }

    @Before
    public void tareDown() {

    }

    @Test
    public void testInit() {
        assertEquals("test", Kakasi.run("test"));
    }
}
