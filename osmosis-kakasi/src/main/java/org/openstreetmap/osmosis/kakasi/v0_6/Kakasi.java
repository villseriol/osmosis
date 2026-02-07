package org.openstreetmap.osmosis.kakasi.v0_6;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.osmosis.kakasi.common.NativeLoader;
import org.openstreetmap.osmosis.kakasi.common.kakasi;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.DuplicateSpaceTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.KakasiTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.Latin1Transformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.TrimTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.UnaccentTransformer;

public class Kakasi {
    private static List<KakasiTransformer> inputTransformers = new ArrayList<>();
    private static List<KakasiTransformer> outputTransformers = new ArrayList<>();

    static {
        NativeLoader.load();

        registerInputTransformer(new UnaccentTransformer());
        registerInputTransformer(new Latin1Transformer());

        registerOutputTransformer(new TrimTransformer());
        registerOutputTransformer(new DuplicateSpaceTransformer());
    }

    public static void registerOutputTransformer(KakasiTransformer transformer) {
        outputTransformers.add(transformer);
    }

    public static void registerInputTransformer(KakasiTransformer transformer) {
        inputTransformers.add(transformer);
    }

    public static void configure(KakasiConfig config) {
        String[] argv = config.getArguments();
        int argc = argv.length;

        kakasi.kakasi_getopt_argv(argc, argv);
    }

    public static String run(String input) {
        String in = input;
        for (KakasiTransformer t : inputTransformers) {
            in = t.transform(in);
        }

        String out = kakasi.kakasi_do(in);
        for (KakasiTransformer t : outputTransformers) {
            out = t.transform(out);
        }

        return out;
    }
}
