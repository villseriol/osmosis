package org.openstreetmap.osmosis.kakasi.v0_6;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KakasiConfig {
    public static KakasiConfig DEFAULT_CONFIG = new KakasiConfig() {
        {
            setInputCharset(Charset.forName("utf8"));
            setOutputCharset(Charset.forName("utf8"));
            setSeparatorEnabled(true);
            setTranslations(
                    new HashSet<>() {
                        {
                            add(new KakasiTranslation(KakasiCharset.HIRAGANA, KakasiCharset.ASCII));
                            add(new KakasiTranslation(KakasiCharset.KANJI, KakasiCharset.ASCII));
                            add(new KakasiTranslation(KakasiCharset.KATAKANA_JIS, KakasiCharset.ASCII));
                            add(new KakasiTranslation(KakasiCharset.SIGN, KakasiCharset.ASCII));
                            add(new KakasiTranslation(KakasiCharset.KATAKANA, KakasiCharset.ASCII));
                        }
                    });
        }
    };

    private Charset inputCharset = Charset.defaultCharset();
    private Charset outputCharset = Charset.defaultCharset();
    private Set<KakasiTranslation> translations = new HashSet<>();
    private boolean isSeparatorEnabled;
    private String separator;

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

    public Charset getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(Charset inputCharset) {
        this.inputCharset = inputCharset;
    }

    public Charset getOutputCharset() {
        return outputCharset;
    }

    public void setOutputCharset(Charset outputCharset) {
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
        arguments.add(String.format("-i%s", inputCharset.name()));
        arguments.add(String.format("-o%s", outputCharset.name()));

        if (isSeparatorEnabled) {
            arguments.add("-s");

            if (separator != null && !"".equals(separator)) {
                arguments.add(String.format("-S\"%s\"", separator));
            }
        }

        for (KakasiTranslation t : translations) {
            arguments.add(String.format("-%s%s", t.getFrom().getCode(), t.getTo().getCode()));
        }

        return arguments.toArray(new String[] {});
    }
}
