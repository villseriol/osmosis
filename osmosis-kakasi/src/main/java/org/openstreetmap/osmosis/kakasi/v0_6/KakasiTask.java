package org.openstreetmap.osmosis.kakasi.v0_6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.core.task.v0_6.SinkSource;

public class KakasiTask implements SinkSource {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Sink sink;
    private String userDictDir;
    private String tagRegex;
    private KakasiConfig config = KakasiConfig.createDefaultConfig();

    public KakasiTask(final String userDictDir, final String tagRegex) {
        this.userDictDir = userDictDir;
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
                String value = Kakasi.run(tag.getValue());
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

        if (userDictDir != null && !"".equals(userDictDir)) {
            try {
                Path dir = Paths.get(userDictDir);
                Set<String> dictionaries = new HashSet<>();
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                    for (Path file : stream) {
                        dictionaries.add(String.valueOf(file.getFileName()));
                    }
                }

                logger.info("Loaded user dictionaries:");
                for (String d : dictionaries) {
                    logger.info(d);
                }

                config.setDictionaries(dictionaries);
            } catch (IOException e) {
                logger.log(Level.WARNING, String.format("Failed to load user dictionary: %s", userDictDir));
            }
        }

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
