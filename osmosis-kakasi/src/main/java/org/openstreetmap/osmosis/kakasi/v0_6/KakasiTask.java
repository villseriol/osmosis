// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

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
import org.openstreetmap.osmosis.kakasi.v0_6.configuration.UserConfiguration;
import org.openstreetmap.osmosis.kakasi.v0_6.configuration.UserConfigurationLoader;


public class KakasiTask implements SinkSource {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private JpnToEng translator = JpnToEng.getInstance();
    private UserConfigurationLoader loader = UserConfigurationLoader.getInstance();

    private Sink sink;
    private String configFile;
    private UserConfiguration configuration = new UserConfiguration();

    public KakasiTask(final String configFile) {
        logger.log(Level.FINE, "Kakasi configured with " + configFile);
        this.configFile = configFile;
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
            boolean isMatch = configuration.getTagMatchs().stream().anyMatch((t) -> t.isMatch(key));
            if (isMatch) {
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

        this.configuration = loader.load(configFile);

        translator.init(configuration);
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
