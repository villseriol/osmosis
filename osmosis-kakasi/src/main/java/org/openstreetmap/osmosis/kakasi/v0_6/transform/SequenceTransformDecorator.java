// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

import java.util.ArrayList;
import java.util.List;


/**
 * A decorator for applying transforms in a specific order.
 */
public class SequenceTransformDecorator extends TransformDecorator {
    private final List<Transform> targets = new ArrayList<>();

    public SequenceTransformDecorator(Transform target, Transform... rest) {
        super(target);

        this.targets.add(target);
        for (Transform t : rest) {
            this.targets.add(t);
        }
    }


    /**
     * Transforms the provided string using the transforms in order.
     *
     * @param input the string to transform
     */
    @Override
    public String action(String input) {
        String result = input;
        for (Transform t : targets) {
            result = t.action(result);
        }

        return result;
    }
}
