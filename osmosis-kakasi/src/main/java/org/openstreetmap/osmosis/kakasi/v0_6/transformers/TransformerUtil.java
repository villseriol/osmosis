package org.openstreetmap.osmosis.kakasi.v0_6.transformers;

import java.util.ArrayList;
import java.util.List;

public class TransformerUtil {
    private static List<Transformer> inputTransformers = new ArrayList<>();
    private static List<Transformer> outputTransformers = new ArrayList<>();

    static {
        registerInputTransformer(UnaccentTransformer.getInstance());
        registerInputTransformer(Latin1Transformer.getInstance());

        registerOutputTransformer(TrimTransformer.getInstance());
        registerOutputTransformer(DuplicateSpaceTransformer.getInstance());

        // could use class registration and reflection to initialize here, instead make a call to loadTransformers
        // Transformer composed = outputTransformers.stream()
        // .reduce(s -> s, (a, b) -> s -> b.transform(a.transform(s)));
    }

    public static void load() {
        // load the transformers here (for eventual refactor)
    }

    public static String pre(final String input) {
        String out = input;
        for (Transformer t : inputTransformers) {
            out = t.transform(out);
        }

        return out;
    }

    public static String post(final String input) {
        String out = input;
        for (Transformer t : outputTransformers) {
            out = t.transform(out);
        }

        return out;
    }

    public static void registerOutputTransformer(Transformer transformer) {
        if (transformer != null) {
            outputTransformers.add(transformer);
        }
    }

    public static void registerInputTransformer(Transformer transformer) {
        if (transformer != null) {
            inputTransformers.add(transformer);
        }
    }
}
