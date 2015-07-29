package org.rogotulka.foxfilter.shader;

import android.opengl.GLES20;

/**
 *
 */
public class ShaderUtils {
    public static int loadShader(int type, Shader shader){
        int glShader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(glShader, shader.);
    }

}
