package org.openstreetmap.osmosis.kakasi.v0_6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KakasiConfig {
    private KakasiInOutEncoding inputCharset;
    private KakasiInOutEncoding outputCharset;
    private Set<KakasiTranslation> translations = new HashSet<>();
    private Set<String> dictionaries = new HashSet<>();
    private boolean isSeparatorEnabled;
    private String separator;

    public static KakasiConfig createDefaultConfig() {
        return new KakasiConfig() {
            {
                setInputCharset(KakasiInOutEncoding.EUC);
                setOutputCharset(KakasiInOutEncoding.UTF8);
                setSeparatorEnabled(true);
                setTranslations(
                        new HashSet<>() {
                            {
                                add(new KakasiTranslation(KakasiCharsetCategory.HIRAGANA, KakasiCharsetCategory.ASCII));
                                add(new KakasiTranslation(KakasiCharsetCategory.KANJI, KakasiCharsetCategory.ASCII));
                                add(new KakasiTranslation(KakasiCharsetCategory.KATAKANA_JIS,
                                        KakasiCharsetCategory.ASCII));
                                add(new KakasiTranslation(KakasiCharsetCategory.SIGN, KakasiCharsetCategory.ASCII));
                                add(new KakasiTranslation(KakasiCharsetCategory.KATAKANA, KakasiCharsetCategory.ASCII));
                            }
                        });
            }
        };
    }

    public Set<String> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Set<String> dictionaries) {
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

    public KakasiInOutEncoding getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(KakasiInOutEncoding inputCharset) {
        this.inputCharset = inputCharset;
    }

    public KakasiInOutEncoding getOutputCharset() {
        return outputCharset;
    }

    public void setOutputCharset(KakasiInOutEncoding outputCharset) {
        this.outputCharset = outputCharset;
    }

    public void setTranslations(Set<KakasiTranslation> translations) {
        this.translations = translations;
    }

    public Set<KakasiTranslation> getTranslations() {
        return translations;
    }

    public String[] getArguments() {
        List<String> arguments = new ArrayList<>();
        if (inputCharset != null) {
            arguments.add(String.format("-i%s", inputCharset.getCode()));
        }

        if (outputCharset != null) {
            arguments.add(String.format("-o%s", outputCharset.getCode()));
        }

        if (isSeparatorEnabled) {
            arguments.add("-s");

            if (separator != null && !"".equals(separator)) {
                arguments.add(String.format("-S\"%s\"", separator));
            }
        }

        for (KakasiTranslation t : translations) {
            arguments.add(String.format("-%s%s", t.getFrom().getCode(), t.getTo().getCode()));
        }

        for (String d : dictionaries) {
            arguments.add(d);
        }

        return arguments.toArray(new String[] {});
    }
}
