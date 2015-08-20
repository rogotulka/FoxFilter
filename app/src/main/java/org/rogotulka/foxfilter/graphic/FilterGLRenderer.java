package org.rogotulka.foxfilter.graphic;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.rogotulka.foxfilter.filter.Filter;
import org.rogotulka.foxfilter.filter.OrdinaryFilter;

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

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Filter getFilter() {
        return mFilter;
    }

    public void setFilter(Filter filter) {
        mFilter = filter;
    }
}
