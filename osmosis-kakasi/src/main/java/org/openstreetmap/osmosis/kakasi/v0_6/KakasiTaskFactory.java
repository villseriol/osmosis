package org.openstreetmap.osmosis.kakasi.v0_6;

import org.openstreetmap.osmosis.core.pipeline.common.TaskConfiguration;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManager;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.pipeline.v0_6.SinkSourceManager;

public class KakasiTaskFactory extends TaskManagerFactory {
    @Override
    protected TaskManager createTaskManagerImpl(TaskConfiguration taskConfig) {
        String dictDir = getStringArgument(taskConfig, "dictPath", null);
        String tagRegex = getStringArgument(taskConfig, "tagRegex");

        return new SinkSourceManager(taskConfig.getId(), new KakasiTask(dictDir, tagRegex),
                taskConfig.getPipeArgs());
    }

}
