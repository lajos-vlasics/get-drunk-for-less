package com.lalikum.getdrunkforless.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DecodeBitmap {

    public static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeResource(res, id, options);
                Log.d("DecodeBitmap", "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e("DecodeBitmap", "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }
        return bitmap;
    }
}
