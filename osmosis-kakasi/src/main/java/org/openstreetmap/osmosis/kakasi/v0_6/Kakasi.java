package org.openstreetmap.osmosis.kakasi.v0_6;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openstreetmap.osmosis.kakasi.common.NativeLoader;
import org.openstreetmap.osmosis.kakasi.common.jni.kakasi;


public class Kakasi {
    private static final Logger LOGGER = Logger.getLogger(Kakasi.class.getName());
    private static Charset EUC_JP = Charset.forName("EUC-JP");

    static {
        NativeLoader.load();
    }

    /**
     * Configure that kakasi shared library globally using the specified config
     *
     * @param config the config
     */
    public static void configure(final KakasiConfig config) {
        String[] argv = config.getArguments();

        LOGGER.log(Level.FINER, "Configuring libkakasi2");
        LOGGER.log(Level.FINER, String.join(" ", argv));

        int success = kakasi.kakasi_getopt_argv(argv);
        if (success != 0) {
            LOGGER.log(Level.WARNING, "Failed during configuration of libkakasi2");
        }
    }


    /**
     * Run kakasi on the provided string and return a converted string.
     *
     * @param input the string
     * @return the converted string
     */
    public static String run(final String input) {
        StringBuilder sb = new StringBuilder(input);
        boolean isNullTerminated = input.endsWith("\0");
        if (!isNullTerminated) {
            sb.append("\0");
        }

        byte[] encodedIn = encodeToEuc(sb.toString());

        return kakasi.kakasi_do(encodedIn);
    }


    /**
     * Encode the provided string into a byte array using the EUC-JP encoding.
     *
     * @param input the string
     * @return the byte array
     */
    private static byte[] encodeToEuc(String input) {
        return input.getBytes(EUC_JP);
    }


    /**
     * Encode the provided string into a byte array using the UTF-8 encoding.
     *
     * @param input the string
     * @return the byte array
     */
    private static byte[] encodeToUtf8(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
}
