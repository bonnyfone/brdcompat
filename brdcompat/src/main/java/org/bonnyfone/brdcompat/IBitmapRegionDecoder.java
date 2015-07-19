package org.bonnyfone.brdcompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Common interface for BitmapRegionDecoder
 */
interface IBitmapRegionDecoder {

    /**
     * Decodes a rectangle region in the image specified by rect.
     * @param rect
     * @param options
     * @return
     */
    Bitmap decodeRegion(Rect rect, BitmapFactory.Options options);

    /**
     * Returns the original image's height
     * @return
     */
    int	getHeight();


    /**
     * Returns the original image's width
     * @return
     */
    int	getWidth();

    /**
     * Returns true if this region decoder has been recycled.
     * @return
     */
    boolean isRecycled();

    /**
     * Frees up the memory associated with this region decoder, and mark the region decoder as "dead",
     * meaning it will throw an exception if decodeRegion(), getWidth() or getHeight() is called.
     */
    void recycle();
}
