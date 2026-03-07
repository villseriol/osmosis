// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.configuration;

import org.openstreetmap.osmosis.core.domain.v0_6.Tag;


public interface TagMatch {
    boolean isMatch(Tag tag);


    boolean isMatch(String value);
}
