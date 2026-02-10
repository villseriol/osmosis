package org.openstreetmap.osmosis.kakasi.v0_6;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openstreetmap.osmosis.kakasi.common.NativeLoader;
import org.openstreetmap.osmosis.kakasi.common.jni.kakasi;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.DuplicateSpaceTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.KakasiTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.Latin1Transformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.TrimTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.UnaccentTransformer;

public class Kakasi {
    private static final Logger LOGGER = Logger.getLogger(Kakasi.class.getName());

    private static List<KakasiTransformer> inputTransformers = new ArrayList<>();
    private static List<KakasiTransformer> outputTransformers = new ArrayList<>();
    private static Charset EUC_JP = Charset.forName("EUC-JP");

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

        LOGGER.log(Level.FINER, "Configuring kakasi");
        LOGGER.log(Level.FINER, String.join(" ", argv));

        kakasi.kakasi_getopt_argv(argv);
    }

    public static String run(String input) {
        String in = input;
        for (KakasiTransformer t : inputTransformers) {
            in = t.transform(in);
        }

        byte[] encodedIn = addNullTermination(encodeToEuc(in));
        String out = kakasi.kakasi_do(encodedIn);

        for (KakasiTransformer t : outputTransformers) {
            out = t.transform(out);
        }

        return out;
    }

    private static byte[] addNullTermination(byte[] input) {
        if (input.length <= 0) {
            return input;
        }

        byte last = input[input.length - 1];
        if (0x00 != last) {
            ByteArrayOutputStream terminated = new ByteArrayOutputStream(input.length + 1);
            terminated.write(input, 0, input.length);
            terminated.write(0x00);

            return terminated.toByteArray();
        }

        return input;
    }

    private static byte[] encodeToEuc(String input) {
        return input.getBytes(EUC_JP);
    }

    private static byte[] encodeToUtf8(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
}
