package org.rogotulka.foxfilter.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
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
        mGLSurfaceView.setEGLContextClientVersion(2);
        FilterGLRenderer renderer = new FilterGLRenderer(getApplicationContext());
        //renderer.setImageBitmap();
       // renderer.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.adium));
        mGLSurfaceView.setRenderer(renderer);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
