// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

public abstract class TransformDecorator implements Transform {
    private final Transform target;

    public TransformDecorator(Transform target) {
        this.target = target;
    }


    public Transform getTarget() {
        return target;
    }
}
