package org.rogotulka.foxfilter.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;

import org.rogotulka.foxfilter.R;
import org.rogotulka.foxfilter.graphic.FilterGLRenderer;
import org.rogotulka.foxfilter.graphic.FilterGLSurfaceView;

import java.io.IOException;


public class FoxFilterActivity extends Activity {

    private FilterGLSurfaceView mGLSurfaceView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                this.getResources().getResourcePackageName(R.drawable.fox) + '/' +
                this.getResources().getResourceTypeName(R.drawable.fox) + '/' +
                this.getResources().getResourceEntryName(R.drawable.fox));
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            
            //// TODO: 29.08.15  
        }
        mGLSurfaceView = (FilterGLSurfaceView) findViewById(R.id.surfaceView);
        mGLSurfaceView.setEGLContextClientVersion(2);
        FilterGLRenderer renderer = new FilterGLRenderer();
        renderer.setBitmap(bitmap);
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
