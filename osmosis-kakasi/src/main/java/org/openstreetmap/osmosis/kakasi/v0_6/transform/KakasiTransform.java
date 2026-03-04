// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import org.villseriol.kakasi.api.Kakasi;


public class KakasiTransform implements Transform {
    @Override
    public String action(String input) {
        return Kakasi.run(input);
    }
}
