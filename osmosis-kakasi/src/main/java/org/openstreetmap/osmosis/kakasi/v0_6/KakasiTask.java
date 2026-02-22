// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openstreetmap.osmosis.core.OsmosisRuntimeException;
import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.core.task.v0_6.SinkSource;
import org.openstreetmap.osmosis.kakasi.common.DictionaryLoader;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.DuplicateSpaceTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.Latin1Transformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.Transformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.TransformerUtil;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.TrimTransformer;
import org.openstreetmap.osmosis.kakasi.v0_6.transformers.UnaccentTransformer;


public class KakasiTask implements SinkSource {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Sink sink;
    private String dictPaths;
    private String dictNames;
    private String tagRegex;
    private KakasiConfig config = KakasiConfig.createDefaultConfig();
    private TransformerUtil transformerUtil = TransformerUtil.getInstance();

    public KakasiTask(final String dictPaths, final String tagRegex, final String dictNames) {
        this.dictPaths = dictPaths;
        this.dictNames = dictNames;
        this.tagRegex = tagRegex;
    }


    @Override
    public void process(EntityContainer entityContainer) {
        EntityContainer writeableEntityContainer = entityContainer.getWriteableInstance();
        Entity entity = entityContainer.getEntity();
        Collection<Tag> entityTags = entity.getTags();
        EntityType entityType = entity.getType();

        Collection<Tag> removed = new HashSet<>();
        Collection<Tag> updated = new HashSet<>();

        logger.log(Level.FINER, String.format("Starting translation for %s", entity.getId()));

        Transformer inputT = transformerUtil.getComposedInputTransformer();
        Transformer outputT = transformerUtil.getComposedOutputTransformer();

        for (Tag tag : entityTags) {
            String key = tag.getKey();
            if (key.matches(tagRegex)) {
                String original = tag.getValue();
                String value = outputT.transform(Kakasi.run(inputT.transform(original)));
                Tag next = new Tag(key, value);

                logger.log(Level.FINER, String.format("%s: (%s, %s)", key, original, value));

                removed.add(tag);
                updated.add(next);
            }
        }

        logger.log(Level.FINER, "Completed translation");

        entityTags.removeAll(removed);
        entityTags.addAll(updated);

        sink.process(writeableEntityContainer);
    }


    @Override
    public void initialize(Map<String, Object> metaData) {
        sink.initialize(metaData);

        Set<String> dictionaries = new HashSet<>();

        if (dictNames != null && !"".equals(dictNames)) {
            logger.info("Loading pre-compiled dictionaries");

            String[] names = dictNames.split(",");
            for (String name : names) {

                String message = String.format("Loading dictionary: %s", name);
                logger.info(message);

                try {
                    Path path = DictionaryLoader.load(name);
                    dictionaries.add(path.toString());
                } catch (Exception e) {
                    String error = String.format("Dictionary could not be loaded: %s", name);
                    throw new OsmosisRuntimeException(error, e);
                }
            }
        }

        if (dictPaths != null && !"".equals(dictPaths)) {
            String[] paths = dictPaths.split(";");
            for (String part : paths) {
                Path path = Path.of(part);

                String message = String.format("Loading: %s", part);
                logger.info(message);

                if (Files.exists(path)) {
                    dictionaries.add(part);
                } else {
                    String error = String.format("Dictionary does not exist: %s", path);
                    throw new OsmosisRuntimeException(error);
                }
            }
        }

        config.setDictionaries(dictionaries);

        transformerUtil.registerInputTransformer(UnaccentTransformer.getInstance());
        transformerUtil.registerInputTransformer(Latin1Transformer.getInstance());

        transformerUtil.registerOutputTransformer(TrimTransformer.getInstance());
        transformerUtil.registerOutputTransformer(DuplicateSpaceTransformer.getInstance());

        Kakasi.configure(config);
    }


    @Override
    public void complete() {
        sink.complete();
    }


    @Override
    public void close() {
        sink.close();
    }


    @Override
    public void setSink(Sink sink) {
        this.sink = sink;
    }
}
