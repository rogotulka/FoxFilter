package org.rogotulka.foxfilter.filter;

import android.graphics.Color;
import android.opengl.GLES20;

import org.rogotulka.foxfilter.shader.ShaderUtils;

/**
 * Created by user on 06.08.2015.
 */
public class ColorFilter extends OrdinaryFilter {


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
                        "  lowp vec4 base = texture2D( s_texture, v_texCoord );" +
                        "  float linear_luminance =  0.2126 * base.r + 0.7125 * base.g + 0.0722 * base.b;"+
                        "  gl_FragColor = vec4(linear_luminance, linear_luminance, linear_luminance, 1.0);" +
                        "}";
    }



}
