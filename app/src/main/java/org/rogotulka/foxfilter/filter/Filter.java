package org.rogotulka.foxfilter.filter;


import java.nio.FloatBuffer;

public interface Filter {
    void filter(int textureId, FloatBuffer cubeBuffer);
    int load();
}
