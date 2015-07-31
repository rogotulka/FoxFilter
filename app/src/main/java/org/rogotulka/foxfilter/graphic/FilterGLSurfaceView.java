package org.rogotulka.foxfilter.graphic;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;


/**
 * Custom OpenGL ES container
 */
public class FilterGLSurfaceView extends GLSurfaceView {

    public FilterGLSurfaceView(Context context) {
        super(context);
        // setup
        setEGLContextClientVersion(2);
        setRenderer(new FilterGLRenderer());
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public FilterGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
