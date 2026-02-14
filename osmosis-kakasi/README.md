```sh
    swig \
    -outdir '/home/ey4180/dev/osmosis/osmosis-kakasi/src/main/java/org/openstreetmap/osmosis/kakasi/common/jni' \
    -package org.openstreetmap.osmosis.kakasi.common.jni \
    -I/usr/include \
    -java '/home/ey4180/dev/osmosis/osmosis-kakasi/src/main/resources/kakasi.i'
```

```sh
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

gcc -fPIC -shared kakasi_wrap.c \
  -I"$JAVA_HOME/include" \
  -I"$JAVA_HOME/include/linux" \
  -L"/usr/lib" -lkakasi \
  -o libkakasi_jni.so
```

```
pkill -f '.*GradleDaemon.*'
sudo update-alternatives --config java
```