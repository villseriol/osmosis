// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

public class TrimTransform implements Transform {
    @Override
    public String action(String input) {
        return input.trim();
    }
}
