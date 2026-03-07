// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.common;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.openstreetmap.osmosis.kakasi.v0_6.configuration.UserConfiguration;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.HalfToFullTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.KakasiTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.LigatureTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.ReplacementTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.SequenceTransformDecorator;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.SplitTransformDecorator;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.Transform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.TransformProxy;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.TrimTransform;
import org.openstreetmap.osmosis.kakasi.v0_6.transform.UnAccentTransform;
import org.villseriol.kakasi.api.Kakasi;
import org.villseriol.kakasi.api.KakasiConfig;


public final class KakasiPipeline {
    private static KakasiPipeline instance;

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private TransformProxy pre = new TransformProxy();
    private TransformProxy post = new TransformProxy();
    private Transform combined = new SequenceTransformDecorator(
            // 1. split the string into runs of words
            new SplitTransformDecorator(
                    // 1. apply the transforms in this order
                    new SequenceTransformDecorator(
                            // 1. replace all characters except japanese ones to the latin1 codepage
                            new UnAccentTransform(), new LigatureTransform(), new HalfToFullTransform(),
                            // 2. user pre-transform
                            pre,
                            // 3. run kakasi on the string
                            new KakasiTransform())),
            // 1. trim the white space
            new TrimTransform(),
            // 2. user post-transform
            post);

    private KakasiConfig config = KakasiConfig.createDefaultConfig();

    private KakasiPipeline() {
        super();
    }


    public static KakasiPipeline getInstance() {
        if (instance == null) {
            instance = new KakasiPipeline();
        }

        return instance;
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


    public void init() {
        Kakasi.configure(config);
    }


    public void init(UserConfiguration configuration) {
        List<String> dictionaries = configuration.getDictionaries().stream().map((p) -> p.toString()).toList();
        if (!dictionaries.isEmpty()) {
            logger.info("Loaded " + dictionaries.size() + " dictionaries.");
            this.config.setDictionaries(dictionaries);
        }

        Kakasi.configure(config);

        Map<CharSequence, CharSequence> replacements = configuration.getReplacements().stream()
                .collect(Collectors.toMap((r) -> r.getFrom(), (r) -> r.getTo()));
        this.pre.setProxy(new ReplacementTransform(replacements));

    }


    public String run(String input) {
        return combined.action(input);
    }
}
