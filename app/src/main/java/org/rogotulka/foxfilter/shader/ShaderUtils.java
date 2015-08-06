package org.rogotulka.foxfilter.shader;

import android.opengl.GLES20;
import android.util.Log;

/**
 *
 */
public class ShaderUtils {

    public static int loadShader(int type, String shaderStr){
        int glShader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(glShader, shaderStr);
        GLES20.glCompileShader(glShader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(glShader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(glShader));
            return 0;
        }
        return glShader;
    }

}
