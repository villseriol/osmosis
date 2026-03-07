// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.configuration;

import org.openstreetmap.osmosis.core.domain.v0_6.Tag;


public class TagKeyMatch implements TagMatch {
    private final String pattern;

    public TagKeyMatch(String pattern) {
        this.pattern = pattern;
    }


    public String getPattern() {
        return pattern;
    }


    @Override
    public boolean isMatch(Tag tag) {
        return isMatch(tag.getKey());
    }


    @Override
    public boolean isMatch(String tag) {
        return tag.matches(pattern);
    }
}
