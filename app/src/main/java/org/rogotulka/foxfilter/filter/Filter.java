package org.rogotulka.foxfilter.filter;


import java.nio.FloatBuffer;

public abstract class Filter {
    public static int mGLProgId = -1;

    public abstract void filter(int textureId, FloatBuffer cubeBuffer);

    public abstract int load();
}
