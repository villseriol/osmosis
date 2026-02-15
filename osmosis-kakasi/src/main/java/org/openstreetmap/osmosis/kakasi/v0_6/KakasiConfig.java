// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class KakasiConfig {
    // Do not change the input and output encoding configuration, this java wrapper abstracts from this concept
    private static final KakasiInOutEncoding INPUT_ENCODING = KakasiInOutEncoding.EUC;
    private static final KakasiInOutEncoding OUTPUT_ENCODING = KakasiInOutEncoding.UTF8;

    private Collection<KakasiTranslation> translations = new ArrayList<>();
    private Collection<String> dictionaries = new ArrayList<>();
    private boolean isSeparatorEnabled;
    private boolean isCapitalize;
    private boolean isUpperCase;
    private String separator;

    public static KakasiConfig createDefaultConfig() {
        return new KakasiConfig() {
            {
                setSeparatorEnabled(true);
                setTranslations(new HashSet<>() {
                    {
                        add(new KakasiTranslation(KakasiCharsetCategory.HIRAGANA, KakasiCharsetCategory.ASCII));
                        add(new KakasiTranslation(KakasiCharsetCategory.KANJI, KakasiCharsetCategory.ASCII));
                        add(new KakasiTranslation(KakasiCharsetCategory.KATAKANA_JIS, KakasiCharsetCategory.ASCII));
                        add(new KakasiTranslation(KakasiCharsetCategory.SIGN, KakasiCharsetCategory.ASCII));
                        add(new KakasiTranslation(KakasiCharsetCategory.KATAKANA, KakasiCharsetCategory.ASCII));
                    }
                });
            }
        };
    }


    public void setUpperCase(boolean isUpperCase) {
        this.isUpperCase = isUpperCase;
    }


    public boolean isUpperCase() {
        return isUpperCase;
    }


    public void setCapitalize(boolean isCapitalize) {
        this.isCapitalize = isCapitalize;
    }


    public boolean isCapitalize() {
        return isCapitalize;
    }


    public Collection<String> getDictionaries() {
        return dictionaries;
    }


    public void setDictionaries(Collection<String> dictionaries) {
        this.dictionaries = dictionaries;
    }


    public boolean isSeparatorEnabled() {
        return isSeparatorEnabled;
    }


    public void setSeparatorEnabled(boolean isSeparatorEnabled) {
        this.isSeparatorEnabled = isSeparatorEnabled;
    }


    public void setSeparator(String separator) {
        this.separator = separator;
    }


    public String getSeparator() {
        return separator;
    }


    public void setTranslations(Collection<KakasiTranslation> translations) {
        this.translations = translations;
    }


    public Collection<KakasiTranslation> getTranslations() {
        return translations;
    }


    public String[] getArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.add(String.format("-i%s", INPUT_ENCODING.getCode()));
        arguments.add(String.format("-o%s", OUTPUT_ENCODING.getCode()));

        if (isSeparatorEnabled) {
            arguments.add("-s");

            if (separator != null && !"".equals(separator)) {
                arguments.add(String.format("-S\"%s\"", separator));
            }
        }

        if (isUpperCase) {
            arguments.add("U");
        }

        if (isCapitalize) {
            arguments.add("-C");
        }

        for (KakasiTranslation t : translations) {
            arguments.add(String.format("-%s%s", t.getFrom().getCode(), t.getTo().getCode()));
        }

        for (String d : dictionaries) {
            arguments.add(d);
        }

        return arguments.toArray(String[]::new);
    }
}
