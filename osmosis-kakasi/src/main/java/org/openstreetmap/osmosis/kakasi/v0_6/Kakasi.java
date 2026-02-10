package org.openstreetmap.osmosis.kakasi.v0_6;

import java.io.ByteArrayOutputStream;
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

    public static void configure(KakasiConfig config) {
        String[] argv = config.getArguments();

        LOGGER.log(Level.FINER, "Configuring libkakasi2");
        LOGGER.log(Level.FINER, String.join(" ", argv));

        kakasi.kakasi_getopt_argv(argv);
    }

    public static String run(String input) {
        byte[] encodedIn = addNullTermination(encodeToEuc(input));

        return kakasi.kakasi_do(encodedIn);
    }

    private static byte[] addNullTermination(byte[] input) {
        if (input.length <= 0) {
            return input;
        }

        byte last = input[input.length - 1];
        if (0x00 != last) {
            ByteArrayOutputStream terminated = new ByteArrayOutputStream(input.length + 1);
            terminated.writeBytes(input);
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
