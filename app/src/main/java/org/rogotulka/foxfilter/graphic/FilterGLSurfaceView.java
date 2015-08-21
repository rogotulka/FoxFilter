package org.rogotulka.foxfilter.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import org.rogotulka.foxfilter.R;
import org.rogotulka.foxfilter.filter.OrdinaryFilter;


/**
 * Custom OpenGL ES container
 */

public class FilterGLSurfaceView extends GLSurfaceView {




    public FilterGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
