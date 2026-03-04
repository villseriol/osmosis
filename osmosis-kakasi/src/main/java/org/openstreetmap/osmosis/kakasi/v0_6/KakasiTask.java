// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.core.task.v0_6.SinkSource;
import org.openstreetmap.osmosis.kakasi.common.JpnToEng;


public class KakasiTask implements SinkSource {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private JpnToEng translator = JpnToEng.getInstance();

    private Sink sink;
    private String dictPaths;
    private String dictNames;
    private String tagRegex;

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

        for (Tag tag : entityTags) {
            String key = tag.getKey();
            if (key.matches(tagRegex)) {
                String original = tag.getValue();
                String value = translator.run(original);
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

        if (dictNames != null && !"".equals(dictNames)) {
            logger.info("Loading pre-compiled dictionaries");

            String[] names = dictNames.split(",");
            for (String name : names) {
                String message = String.format("Loading dictionary: %s", name);
                logger.info(message);

                translator.addDictionaryName(name);
            }
        } else {
            logger.info("No dictionary names provided");
        }

        if (dictPaths != null && !"".equals(dictPaths)) {
            String[] paths = dictPaths.split(";");
            for (String part : paths) {
                Path path = Path.of(part);

                String message = String.format("Loading: %s", part);
                logger.info(message);

                translator.addDictionaryPath(path);
            }
        } else {
            logger.info("No dictionary paths provided");
        }

        translator.init();
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
