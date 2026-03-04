// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.kakasi.v0_6.transform;

public class TransformProxy implements Transform {
    private Transform proxy = new NoTransform();

    @Override
    public String action(String input) {
        return proxy.action(input);
    }


    public Transform getProxy() {
        return proxy;
    }


    public void setProxy(Transform proxy) {
        this.proxy = proxy;
    }
}
