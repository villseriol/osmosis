package org.openstreetmap.osmosis.kakasi;

import java.util.HashMap;
import java.util.Map;

import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.plugin.PluginLoader;
import org.openstreetmap.osmosis.kakasi.v0_6.KakasiTaskFactory;

public class KakasiPlugin implements PluginLoader {
    @Override
    public Map<String, TaskManagerFactory> loadTaskFactories() {
        KakasiTaskFactory transformFactory = new org.openstreetmap.osmosis.kakasi.v0_6.KakasiTaskFactory();

        Map<String, TaskManagerFactory> tasks = new HashMap<String, TaskManagerFactory>();
        tasks.put("kakasi", transformFactory);

        return tasks;
    }
}
