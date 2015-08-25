package org.rogotulka.foxfilter.filter;

import android.opengl.GLES20;

import org.rogotulka.foxfilter.shader.ShaderUtils;

import java.nio.FloatBuffer;

/**
 * Created by user on 06.08.2015.
 */
public class OrdinaryFilter implements Filter {

    public static String VERTEX_SHADER;

    public static String FRAGMENT_SHADER;

    static {
        VERTEX_SHADER =
                "uniform mat4 uMVPMatrix;" +
                        "attribute vec4 vPosition;" +
                        "attribute vec2 a_texCoord;" +
                        "varying vec2 v_texCoord;" +
                        "void main() {" +
                        "  gl_Position = uMVPMatrix * vPosition;" +
                        "  v_texCoord = a_texCoord;" +
                        "}";

        FRAGMENT_SHADER = "precision mediump float;" +
                "varying vec2 v_texCoord;" +
                "uniform sampler2D s_texture;" +
                "void main() {" +
                "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
                "}";
    }

    private int mGLProgId = -1;
    private boolean mIsInitialized;
    private int mGLAttribPosition;
    private int mGLAttribTextureCoordinate;
    private int mGLUniformTexture;


    @Override
    public void filter(int textureId, FloatBuffer cubeBuffer) {

//        GLES20.glUseProgram(mGLProgId);
//        //runPendingOnDrawTasks();
//        if (!mIsInitialized) {
//            return;
//        }
//
//        cubeBuffer.position(0);
//        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, cubeBuffer);
//        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
////        textureBuffer.position(0);
////        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0
////                );
//        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
//        if (textureId != -1) {
//            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
//            GLES20.glUniform1i(mGLUniformTexture, 0);
//        }
////        onDrawArraysPre();
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
//        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

    }

    @Override
    public int load() {
        if(mGLProgId == -1){
            //mGLProgId = ShaderUtils.loadProgram(VERTEX_SHADER, FRAGMENT_SHADER);
            mIsInitialized = true;
        }

        return mGLProgId;
    }
}
