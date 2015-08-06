package org.rogotulka.foxfilter.ui;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;

import org.rogotulka.foxfilter.R;
import org.rogotulka.foxfilter.graphic.FilterGLRenderer;
import org.rogotulka.foxfilter.graphic.FilterGLSurfaceView;


public class FoxFilterActivity extends Activity {

    private FilterGLSurfaceView mGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mGLSurfaceView = (FilterGLSurfaceView) findViewById(R.id.surfaceView);
        mGLSurfaceView.setRenderer(new FilterGLRenderer());
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
