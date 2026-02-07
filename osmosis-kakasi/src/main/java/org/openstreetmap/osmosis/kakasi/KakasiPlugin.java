package org.openstreetmap.osmosis.kakasi;

import java.util.Map;

import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.plugin.PluginLoader;

public class KakasiPlugin implements PluginLoader {
    @Override
    public Map<String, TaskManagerFactory> loadTaskFactories() {
        throw new UnsupportedOperationException("Unimplemented method 'loadTaskFactories'");
    }
}
