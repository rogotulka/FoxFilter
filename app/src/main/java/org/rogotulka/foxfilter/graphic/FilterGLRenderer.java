package org.rogotulka.foxfilter.graphic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import org.rogotulka.foxfilter.filter.Filter;
import org.rogotulka.foxfilter.filter.OrdinaryFilter;
import org.rogotulka.foxfilter.shader.ShaderUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.Queue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Custom GLRenderer
 */
public class FilterGLRenderer implements GLSurfaceView.Renderer {

    private Bitmap mBitmap;
    private Filter mFilter;

    public FilterGLRenderer() {
        mFilter = new OrdinaryFilter();
    }

    //----------------------------------------------------------------------------------------------
    /**
     * GLSurfaceView.Renderer implementation
     */
    //----------------------------------------------------------------------------------------------


    private static final float uvs[] = new float[] {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };



    private int mTextureId = -1;

    //private FloatBuffer verticesBuffer;
    private FloatBuffer textureBuffer;

    private void initBuffers(){
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(uvs);
        textureBuffer.position(0);
    }

    private void loadTexture(){
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        mTextureId = textures[0];

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
        mBitmap.recycle();
    }


    @Override
    public void onSurfaceCreated(final GL10 unused, final EGLConfig config) {
        initBuffers();
        loadTexture();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);
        //mFilter.load();
        GLES20.glUseProgram(mFilter.load());
    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {

    }

    @Override
    public void onDrawFrame(final GL10 gl) {

    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public void setFilter(Filter filter) {
        mFilter = filter;
    }
}
