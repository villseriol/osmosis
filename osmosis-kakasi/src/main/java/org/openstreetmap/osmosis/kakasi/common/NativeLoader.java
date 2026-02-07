package org.openstreetmap.osmosis.kakasi.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeLoader {
    private static boolean loaded = false;

    public static void load() {
        if (loaded)
            return; // load only once

        String libName = "libkakasi_jni.so"; // JNI library compiled for Linux

        try (InputStream in = NativeLoader.class.getResourceAsStream("/native/linux/" + libName)) {
            if (in == null) {
                throw new RuntimeException("Native library not found in resources: " + libName);
            }

            Path temp = Files.createTempFile("libkakasi_jni", ".so");
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
            System.load(temp.toAbsolutePath().toString());
            loaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load native library", e);
        }
    }
}
