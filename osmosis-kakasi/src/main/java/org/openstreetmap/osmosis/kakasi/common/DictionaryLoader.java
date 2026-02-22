// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class DictionaryLoader {
    private static final Map<String, String> RESOURCE_MAP = Map.ofEntries(Map.entry("assoc", "/dict/SKK-JISYO.assoc"),
            Map.entry("china_taiwan", "/dict/SKK-JISYO.china_taiwan"),
            Map.entry("china_taiwan_header", "/dict/SKK-JISYO.china_taiwan.header"),
            Map.entry("edict", "/dict/SKK-JISYO.edict"), Map.entry("edict2", "/dict/SKK-JISYO.edict2"),
            Map.entry("emoji", "/dict/SKK-JISYO.emoji"), Map.entry("fullname", "/dict/SKK-JISYO.fullname"),
            Map.entry("geo", "/dict/SKK-JISYO.geo"), Map.entry("hukugougo", "/dict/SKK-JISYO.hukugougo"),
            Map.entry("itaiji", "/dict/SKK-JISYO.itaiji"), Map.entry("itaiji_jis3_4", "/dict/SKK-JISYO.itaiji.JIS3_4"),
            Map.entry("ivd", "/dict/SKK-JISYO.ivd"), Map.entry("jinmei", "/dict/SKK-JISYO.jinmei"),
            Map.entry("jis2", "/dict/SKK-JISYO.JIS2"), Map.entry("jis2004", "/dict/SKK-JISYO.JIS2004"),
            Map.entry("jis3_4", "/dict/SKK-JISYO.JIS3_4"), Map.entry("l", "/dict/SKK-JISYO.L"),
            Map.entry("law", "/dict/SKK-JISYO.law"), Map.entry("lisp", "/dict/SKK-JISYO.lisp"),
            Map.entry("l_unannotated", "/dict/SKK-JISYO.L.unannotated"), Map.entry("m", "/dict/SKK-JISYO.M"),
            Map.entry("mazegaki", "/dict/SKK-JISYO.mazegaki"), Map.entry("ml", "/dict/SKK-JISYO.ML"),
            Map.entry("noregist", "/dict/SKK-JISYO.noregist"), Map.entry("notes", "/dict/SKK-JISYO.notes"),
            Map.entry("not_wrong", "/dict/SKK-JISYO.not_wrong"), Map.entry("okinawa", "/dict/SKK-JISYO.okinawa"),
            Map.entry("pinyin", "/dict/SKK-JISYO.pinyin"), Map.entry("propernoun", "/dict/SKK-JISYO.propernoun"),
            Map.entry("pubdic_plus", "/dict/SKK-JISYO.pubdic+"), Map.entry("requested", "/dict/SKK-JISYO.requested"),
            Map.entry("s", "/dict/SKK-JISYO.S"), Map.entry("station", "/dict/SKK-JISYO.station"),
            Map.entry("wrong", "/dict/SKK-JISYO.wrong"),
            Map.entry("wrong_annotated", "/dict/SKK-JISYO.wrong.annotated"));

    private static final ConcurrentHashMap<String, Path> CACHE = new ConcurrentHashMap<>();

    private DictionaryLoader() {
        super();
    }


    public static Path load(String key) throws IOException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        String normalizedKey = key.toLowerCase(); // case-insensitive

        return CACHE.computeIfAbsent(normalizedKey, k -> {
            String resourcePath = RESOURCE_MAP.get(k);
            if (resourcePath == null) {
                throw new IllegalArgumentException("Unknown dictionary key: " + key);
            }

            try {
                URL resourceUrl = DictionaryLoader.class.getResource(resourcePath);
                if (resourceUrl == null) {
                    throw new IllegalArgumentException("Resource not found: " + resourcePath);
                }

                try {
                    // Works outside JAR
                    return Path.of(resourceUrl.toURI());
                } catch (URISyntaxException | IllegalArgumentException e) {
                    // Inside JAR: copy to temp file
                    try (InputStream in = DictionaryLoader.class.getResourceAsStream(resourcePath)) {
                        if (in == null) {
                            throw new IllegalArgumentException("Resource not found: " + resourcePath);
                        }
                        String fileName = Path.of(resourcePath).getFileName().toString();
                        Path tempFile = Files.createTempFile("dict-", "-" + fileName);
                        Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
                        tempFile.toFile().deleteOnExit();
                        return tempFile;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load dictionary: " + resourcePath, ex);
            }
        });
    }
}
