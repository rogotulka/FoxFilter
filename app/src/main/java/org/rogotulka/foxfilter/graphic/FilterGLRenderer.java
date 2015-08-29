package org.rogotulka.foxfilter.graphic;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import org.rogotulka.foxfilter.filter.OrdinaryFilter;
import org.rogotulka.foxfilter.shader.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Custom GLRenderer
 */
public class FilterGLRenderer implements GLSurfaceView.Renderer {

    public static float vertices[];
    public static float colors[];
    public static short indices[];
    public static float uvs[];

    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;
    public FloatBuffer uvBuffer;
    public FloatBuffer colorBuffer;
    private float mBoundWidth = 1000;
    private float mBoundHeight = 1000;

    private long mLastTime;
    private int mProgram;
    private OrdinaryFilter mFilter;
    private Bitmap mBitmap;


    public FilterGLRenderer() {
        mFilter = new OrdinaryFilter();
        mLastTime = System.currentTimeMillis() + 100;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();
        if (mLastTime > now) return;

        long elapsed = now - mLastTime;
        Render(mtrxProjectionAndView);
        mLastTime = now;
    }

    private void Render(float[] m) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        int mPositionHandle = GLES20.glGetAttribLocation(ShaderUtils.sp_Image, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        int mColorHandle = GLES20.glGetAttribLocation(ShaderUtils.sp_Image, "a_Color");
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 4,
                GLES20.GL_FLOAT, false,
                0, colorBuffer);

        int mTexCoordLoc = GLES20.glGetAttribLocation(ShaderUtils.sp_Image, "a_texCoord");
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                0, uvBuffer);

        int mtrxhandle = GLES20.glGetUniformLocation(ShaderUtils.sp_Image, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);


        int mSamplerLoc = GLES20.glGetUniformLocation(ShaderUtils.sp_Image, "s_texture");
        GLES20.glUniform1i(mSamplerLoc, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mBoundWidth = width;
        mBoundHeight = height;

        GLES20.glViewport(0, 0, (int) mBoundWidth, (int) mBoundHeight);

        for (int i = 0; i < 16; i++) {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        Matrix.orthoM(mtrxProjection, 0, 0f, mBoundWidth, 0.0f, mBoundHeight, 0, 50);
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        SetupTriangle();
        SetupImage();

        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        int vertexShader = ShaderUtils.loadShader(GLES20.GL_VERTEX_SHADER, ShaderUtils.vs_Image);
        int fragmentShader = ShaderUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderUtils.fs_Image);

        ShaderUtils.sp_Image = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(ShaderUtils.sp_Image, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(ShaderUtils.sp_Image, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(ShaderUtils.sp_Image);                  // creates OpenGL ES program executables
        GLES20.glUseProgram(ShaderUtils.sp_Image);
    }

    public void SetupImage() {
      if(mBitmap == null){
          return;
      }


        uvs = new float[]{
                1.0f, 1.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
                0.0f, 1.0f,

        };

        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
        mBitmap.recycle();

    }

    public void SetupTriangle() {

        vertices = new float[]{
                0.0f, 0f, 0.0f,
                0f, mBoundHeight, 0.0f,
                mBoundWidth, mBoundHeight, 0.0f,
                mBoundWidth, 0f, 0.0f,
        };

        colors = new float[]{
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
                1f, 0f, 1f, 1f,
        };

        indices = new short[]{0, 1, 2, 2, 3, 0};

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);

        ByteBuffer cb = ByteBuffer.allocateDirect(colors.length * 4);
        cb.order(ByteOrder.nativeOrder());
        colorBuffer = cb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void setFilter(OrdinaryFilter filter) {
        mFilter = filter;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
