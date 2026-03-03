// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.openstreetmap.osmosis.core.OsmosisRuntimeException;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.Transformer;
import org.villseriol.kakasi.api.Kakasi;
import org.villseriol.kakasi.api.KakasiConfig;


public final class JpnToEng {
    private static final Set<Character> KAKASI_IGNORE = Set.of('·', '|', ')', '(', ']', '[', '}', '{');
    private static JpnToEng instance;
    private KakasiConfig config = KakasiConfig.createDefaultConfig();
    private List<Transformer> inputTransformers = new ArrayList<>();
    private List<Transformer> outputTransformers = new ArrayList<>();

    private JpnToEng() {
        super();
    }


    public static JpnToEng getInstance() {
        if (instance == null) {
            instance = new JpnToEng();
        }

        return instance;
    }


    public void configure() {
        Kakasi.configure(config);
    }


    public void addOutputTransformer(Transformer transformer) {
        if (transformer != null) {
            outputTransformers.add(transformer);
        }
    }


    public void clearOutputTransformers() {
        this.outputTransformers.clear();
    }


    public void addInputTransformer(Transformer transformer) {
        if (transformer != null) {
            inputTransformers.add(transformer);
        }
    }


    public void clearInputTransformers() {
        this.inputTransformers.clear();
    }


    public void addDictionaryPath(Path path) {
        Collection<String> existing = config.getDictionaries();

        if (Files.exists(path)) {
            existing.add(path.toString());
        } else {
            String error = String.format("Dictionary does not exist: %s", path);
            throw new OsmosisRuntimeException(error);
        }
    }


    public void addDictionaryName(String name) {
        Collection<String> existing = config.getDictionaries();
        try {
            Path path = DictionaryLoader.load(name);
            existing.add(path.toString());
        } catch (Exception e) {
            String error = String.format("Dictionary could not be loaded: %s", name);
            throw new OsmosisRuntimeException(error, e);
        }
    }


    public String run(String input) {
        Transformer inputT = inputTransformers.stream().reduce(s -> s, (a, b) -> s -> b.transform(a.transform(s)));

        StringBuilder original = new StringBuilder(inputT.transform(input));
        StringBuilder result = new StringBuilder();

        // refactor this to use regex, need match, before and after. should be simpler
        StringBuilder buffer = new StringBuilder();
        while (!original.isEmpty()) {
            char current = original.charAt(0);
            if (!KAKASI_IGNORE.contains(current)) {
                buffer.append(current);
            } else {
                // buffer is complete, translate it and move to the next buffer
                String translated = Kakasi.run(buffer.toString());

                result.append(translated);
                result.append(current);

                buffer = new StringBuilder();
            }

            original.deleteCharAt(0);
        }

        Transformer outputT = outputTransformers.stream().reduce(s -> s, (a, b) -> s -> b.transform(a.transform(s)));
        return outputT.transform(result.toString());
    }
}
