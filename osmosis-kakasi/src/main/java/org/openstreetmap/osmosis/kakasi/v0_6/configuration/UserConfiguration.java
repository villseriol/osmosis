// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class UserConfiguration {
    private List<Replacement> replacements = new ArrayList<>();
    private List<Path> dictionaries = new ArrayList<>();
    private List<TagMatch> tagMatches = new ArrayList<>();

    public UserConfiguration() {
        super();
    }


    public List<Path> getDictionaries() {
        return dictionaries;
    }


    public void setDictionaries(List<Path> dictionaries) {
        this.dictionaries = dictionaries;
    }


    public List<Replacement> getReplacements() {
        return replacements;
    }


    public void setReplacements(List<Replacement> replacements) {
        this.replacements = replacements;
    }


    public List<TagMatch> getTagMatchs() {
        return tagMatches;
    }


    public void setTagMatches(List<TagMatch> tagMatches) {
        this.tagMatches = tagMatches;
    }
}
