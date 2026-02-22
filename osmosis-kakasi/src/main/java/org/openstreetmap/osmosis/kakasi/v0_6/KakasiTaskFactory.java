// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6;

import org.openstreetmap.osmosis.core.pipeline.common.TaskConfiguration;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManager;
import org.openstreetmap.osmosis.core.pipeline.common.TaskManagerFactory;
import org.openstreetmap.osmosis.core.pipeline.v0_6.SinkSourceManager;


public class KakasiTaskFactory extends TaskManagerFactory {
    @Override
    protected TaskManager createTaskManagerImpl(TaskConfiguration taskConfig) {
        // a ; separated list of paths that point to a dictionary file
        String dictPaths = getStringArgument(taskConfig, "dictPaths", null);
        // a , separated list of dictionary aliases that come pre-packaged with this plugin
        String dictNames = getStringArgument(taskConfig, "dictNames", null);
        // a regex string to define what tags kakasi should be run on
        String tagRegex = getStringArgument(taskConfig, "tagRegex");

        return new SinkSourceManager(taskConfig.getId(), new KakasiTask(dictPaths, tagRegex, dictNames),
                taskConfig.getPipeArgs());
    }
}
