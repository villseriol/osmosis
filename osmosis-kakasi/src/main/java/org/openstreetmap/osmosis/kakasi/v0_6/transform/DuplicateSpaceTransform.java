// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

public class DuplicateSpaceTransform implements Transform {

    /**
     * Removes all duplicate spaces.
     *
     * @param input the string to transform
     */
    @Override
    public String action(String input) {
        return input.replaceAll("\s+", " ");
    }
}
