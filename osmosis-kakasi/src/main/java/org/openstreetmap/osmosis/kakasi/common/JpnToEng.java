// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.openstreetmap.osmosis.core.OsmosisRuntimeException;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.KakasiTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.Latin1Transform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.SequenceTransformDecorator;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.SplitTransformDecorator;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.Transform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.TransformProxy;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.TrimTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.UnAccentTransform;
import org.villseriol.kakasi.api.Kakasi;
import org.villseriol.kakasi.api.KakasiConfig;


public final class JpnToEng {
    private static JpnToEng instance;

    private TransformProxy pre = new TransformProxy();
    private TransformProxy post = new TransformProxy();
    private Transform combined = new SequenceTransformDecorator(
            // 1. split the string into runs of words
            new SplitTransformDecorator(
                    // 1. apply the transforms in this order
                    new SequenceTransformDecorator(
                            // 1. replace all accented characters with unaccented equivalent
                            new UnAccentTransform(),
                            // 2. replace all characters unknown to latin1 codepage
                            new Latin1Transform(),
                            // 3. user pre-transform
                            pre,
                            // 4. run kakasi on the string
                            new KakasiTransform())),
            // 1. trim the white space
            new TrimTransform(),
            // 2. user post-transform
            post);

    private KakasiConfig config = KakasiConfig.createDefaultConfig();

    private JpnToEng() {
        super();
    }


    public static JpnToEng getInstance() {
        if (instance == null) {
            instance = new JpnToEng();
        }

        return instance;
    }


    public void init() {
        Kakasi.configure(config);
    }


    /**
     * Set a user configurable pre-transform.
     *
     * @param transform the pre-transform to apply
     */
    public void setPreTransform(Transform transform) {
        this.pre.setProxy(transform);
    }


    /**
     * Set a user configurable post-transform.
     *
     * @param transform the post-transform to apply
     */
    public void setPostTransform(Transform transform) {
        this.post.setProxy(transform);
    }


    public void addDictionaryPath(String path) {
        addDictionaryPath(Path.of(path));
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
        return combined.action(input);
    }
}
