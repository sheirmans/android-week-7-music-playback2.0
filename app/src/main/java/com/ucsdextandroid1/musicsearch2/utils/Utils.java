package com.ucsdextandroid1.musicsearch2.utils;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import java.util.Random;

/**
 * Created by rjaylward on 2019-05-11
 */
public class Utils {

    @ColorInt
    public static int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}
