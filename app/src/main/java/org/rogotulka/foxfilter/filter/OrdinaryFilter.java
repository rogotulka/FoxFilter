package org.rogotulka.foxfilter.filter;

import android.opengl.GLES20;

import org.rogotulka.foxfilter.shader.ShaderUtils;

import java.nio.FloatBuffer;

public class OrdinaryFilter extends Filter {

    public static String VERTEX_SHADER;

    public  String FRAGMENT_SHADER;

     {
        VERTEX_SHADER =
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "attribute vec2 a_texCoord;" +
                        "varying vec2 v_texCoord;" +
                        "void main() {" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "  v_texCoord = a_texCoord;" +
                        "}";

        FRAGMENT_SHADER =
                "precision mediump float;" +
                "varying vec2 v_texCoord;" +
                "uniform sampler2D s_texture;" +
                "void main() {" +
                "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
                "}";
    }


    @Override
    public void filter(int textureId, FloatBuffer cubeBuffer) {

    }

    @Override
    public int load() {
        //// TODO: 01.09.15 get rid of not necessary initialized

            int vertexShader = ShaderUtils.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
            int fragmentShader = ShaderUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

            mGLProgId = GLES20.glCreateProgram();
            GLES20.glAttachShader(mGLProgId, vertexShader);
            GLES20.glAttachShader(mGLProgId, fragmentShader);
            GLES20.glLinkProgram(mGLProgId);


        return mGLProgId;
    }
}
