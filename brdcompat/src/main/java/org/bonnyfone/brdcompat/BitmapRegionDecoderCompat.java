package org.bonnyfone.brdcompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * BitmapRegionDecoder can be used to decode a rectangle region from an image.
 * BitmapRegionDecoder is particularly useful when an original image is large and you only need parts of the image. <br>
 * To create a BitmapRegionDecoder, call newInstance(...).
 * Given a BitmapRegionDecoder, users can call decodeRegion() repeatedly to get a decoded Bitmap of the specified region.
 */
public class BitmapRegionDecoderCompat implements IBitmapRegionDecoder {

    private static boolean FORCE_FALLBACK_IMPLEMENTATION = false;

    /**
     * Private empty constructor
     */
    private BitmapRegionDecoderCompat(){}

    /**
     * Constructor which wraps internal implementation
     * @param impl
     */
    private BitmapRegionDecoderCompat(IBitmapRegionDecoder impl){
        this.impl = impl;
    }

    /**
     * Internal implementation object
     */
    private IBitmapRegionDecoder impl;


    /**
     * Check if we are running on Gingerbread or later
     * @return
     */
    private static boolean isAPI10(){
        return !FORCE_FALLBACK_IMPLEMENTATION && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * Force to use the fallback implementation of BitmapRegionDecoder.<br>
     * <b>Should be used only for debug purposes</b>
     * @param forceFallback
     */
    public static void setForceFallbackImplementation(boolean forceFallback){
        FORCE_FALLBACK_IMPLEMENTATION = forceFallback;
    }


    /**
     * Create a BitmapRegionDecoder from a file path.
     * Currently only the JPEG and PNG formats are supported.
     *
     * @param pathName complete path name for the file to be decoded.
     * @param isShareable If this is true, then the BitmapRegionDecoder may keep a shallow reference to
     *                    the input. If this is false, then the BitmapRegionDecoder will explicitly
     *                    make a copy of the input data, and keep that. Even if sharing is allowed,
     *                    the implementation may still decide to make a deep copy of the input data.
     *                    If an image is progressively encoded, allowing sharing may degrade the decoding speed.
     * @return BitmapRegionDecoder, or null if the image data could not be decoded.
     * @throws java.io.IOException if the image format is not supported or can not be decoded.
     */
    public static BitmapRegionDecoderCompat newInstance(String pathName, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderCompat(isAPI10() ? BitmapRegionDecoderNative.newInstance(pathName, isShareable) : BitmapRegionDecoderGinger.newInstance(pathName, isShareable));
    }


    /**
     * Create a BitmapRegionDecoder from an input stream. The stream's position will be where ever it was after the encoded data was read.
     * Currently only the JPEG and PNG formats are supported.
     *
     * @param is The input stream that holds the raw data to be decoded into a BitmapRegionDecoder. <br>
     *           Prior to KITKAT, if is.markSupported() returns true, is.mark(1024) would be called.
     *           As of KITKAT, this is no longer the case.
     *
     * @param isShareable If this is true, then the BitmapRegionDecoder may keep a shallow reference to
     *                    the input. If this is false, then the BitmapRegionDecoder will explicitly
     *                    make a copy of the input data, and keep that. Even if sharing is allowed,
     *                    the implementation may still decide to make a deep copy of the input data.
     *                    If an image is progressively encoded, allowing sharing may degrade the decoding speed.
     *
     * @return BitmapRegionDecoder, or null if the image data could not be decoded.
     *
     * @throws java.io.IOException if the image format is not supported or can not be decoded.
     */
    public static BitmapRegionDecoderCompat newInstance(InputStream is, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderCompat(isAPI10() ? BitmapRegionDecoderNative.newInstance(is, isShareable) : BitmapRegionDecoderGinger.newInstance(is, isShareable));
    }

    /**
     * Create a BitmapRegionDecoder from the file descriptor.
     * The position within the descriptor will not be changed when this returns, so the descriptor can be used again as is.
     * Currently only the JPEG and PNG formats are supported.
     *
     * @param fd The file descriptor containing the data to decode
     * @param isShareable If this is true, then the BitmapRegionDecoder may keep a shallow reference to
     *                    the input. If this is false, then the BitmapRegionDecoder will explicitly
     *                    make a copy of the input data, and keep that. Even if sharing is allowed,
     *                    the implementation may still decide to make a deep copy of the input data.
     *                    If an image is progressively encoded, allowing sharing may degrade the decoding speed.
     *
     * @return BitmapRegionDecoder, or null if the image data could not be decoded.
     *
     * @throws java.io.IOException if the image format is not supported or can not be decoded.
     *
     */
    public static BitmapRegionDecoderCompat newInstance(FileDescriptor fd, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderCompat(isAPI10() ? BitmapRegionDecoderNative.newInstance(fd, isShareable) : BitmapRegionDecoderGinger.newInstance(fd, isShareable));
    }


    /**
     * Create a BitmapRegionDecoder from the specified byte array.
     * Currently only the JPEG and PNG formats are supported.
     *
     * @param data byte array of compressed image data.
     * @param offset offset into data for where the decoder should begin parsing.
     * @param length the number of bytes, beginning at offset, to parse
     * @param isShareable If this is true, then the BitmapRegionDecoder may keep a shallow reference to
     *                    the input. If this is false, then the BitmapRegionDecoder will explicitly
     *                    make a copy of the input data, and keep that. Even if sharing is allowed,
     *                    the implementation may still decide to make a deep copy of the input data.
     *                    If an image is progressively encoded, allowing sharing may degrade the decoding speed.
     *
     * @return BitmapRegionDecoder, or null if the image data could not be decoded.
     *
     * @throws java.io.IOException if the image format is not supported or can not be decoded.
     *
     */
    public static BitmapRegionDecoderCompat newInstance(byte[] data, int offset, int length, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderCompat(isAPI10() ? BitmapRegionDecoderNative.newInstance(data, offset, length, isShareable) : BitmapRegionDecoderGinger.newInstance(data, offset, length, isShareable));
    }

    /**
     * Decodes a rectangle region in the image specified by rect.
     * @param rect The rectangle that specified the region to be decode.
     * @param options null-ok; Options that control downsampling. inPurgeable is not supported.
     *                <br><b>Limited support on API < 11</b>.
     * @return The decoded bitmap, or null if the image data could not be decoded.
     */
    @Override
    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        return impl.decodeRegion(rect, options);
    }

    /**
     * Extracts the "best" rectangle region based on the specified dimensions.
     * The "best" region means the biggest region of the original (downsampled) image, given the required gravity and output sizes/ratio.
     * You should use this method when you have a big source image that have to be fit/be partially displayed.
     * This method will use Gravity.CENTER as default gravity.
     * @param requiredWidth the required Width
     * @param requiredHeight the required Height
     * @return
     */
    public Bitmap decodeBestRegion(int requiredWidth, int requiredHeight){
        return decodeBestRegion(requiredWidth, requiredHeight, Gravity.CENTER);
    }

    /**
     * Extracts the "best" rectangle region based on the specified dimensions.
     * The "best" region means the biggest region of the original (downsampled) image, given the required gravity and output sizes/ratio.
     * You should use this method when you have a big source image that have to be fit/be partially displayed.
     * @param requiredWidth the required Width
     * @param requiredHeight the required Height
     * @param gravity combination of Gravity.LEFT, Gravity.RIGHT, Gravity.TOP,
     *                Gravity.BOTTOM, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL
     * @return
     */
    public Bitmap decodeBestRegion(int requiredWidth, int requiredHeight, int gravity) {

        boolean upscaling = false; //TODO handle upscaling as an option
        int realWidth = getWidth();
        int realHeight = getHeight();

        BitmapFactory.Options   options = new BitmapFactory.Options();


		/* -- Check for upscale -- */
		/* ----------------------- */

        if( ! (requiredWidth <= realWidth && requiredHeight <= realHeight ) ){ //image is smaller than box
            float widthRatio = (float)requiredWidth / realWidth;
            float heightRatio = (float)requiredHeight / realHeight;
            float destinationRatio = Math.max(widthRatio, heightRatio);

            requiredWidth = Math.round(((float)requiredWidth/destinationRatio));
            requiredHeight = Math.round(((float)requiredHeight/destinationRatio));
            upscaling = true;
        }


		/* -- Downsampling -- */
		/* ------------------ */

        boolean limitReached = false;
        int basePow = 0;
        int actualScale;
        while( !limitReached ){
            actualScale = (int) Math.pow(2, basePow+1);
            if( requiredWidth <= realWidth/actualScale && requiredHeight <= realHeight/actualScale){
                basePow++;
            }else{
                limitReached = true;
            }
        }
        //basePow+=1; //extra compression!
        options.inSampleSize = (int) Math.pow(2, basePow);


        int fromTop = 0;
        int fromLeft = 0;

        //Default is Gravity.CENTER
        if(gravity == Gravity.CENTER){
            fromTop = (realHeight - requiredHeight * options.inSampleSize) / 2;
            fromLeft = (realWidth - requiredWidth * options.inSampleSize)  / 2;
        }
        else{
            int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

            if(horizontalGravity == Gravity.LEFT)
                fromLeft = 0;
            else if(horizontalGravity == Gravity.RIGHT)
                fromLeft = realWidth - requiredWidth * options.inSampleSize;
            else if(horizontalGravity == Gravity.CENTER_HORIZONTAL)
                fromLeft = (realWidth - requiredWidth * options.inSampleSize)  / 2;

            if(verticalGravity == Gravity.TOP)
                fromTop = 0;
            else if(verticalGravity == Gravity.BOTTOM)
                fromTop = realHeight - requiredHeight * options.inSampleSize;
            else if(verticalGravity == Gravity.CENTER_VERTICAL)
                fromTop = (realHeight - requiredHeight * options.inSampleSize) / 2;
        }

        Rect area = new Rect(fromLeft, fromTop, fromLeft + requiredWidth * options.inSampleSize, fromTop + requiredHeight * options.inSampleSize);
        return impl.decodeRegion(area, options);

    }

    /**
     * Returns the original image's height
     * @return
     */
    @Override
    public int getHeight() {
        return impl.getHeight();
    }

    /**
     * Returns the original image's width
     * @return
     */
    @Override
    public int getWidth() {
        return impl.getWidth();
    }

    /**
     * Returns true if this region decoder has been recycled.
     * @return true if the region decoder has been recycled
     */
    @Override
    public boolean isRecycled() {
        return impl.isRecycled();
    }

    /**
     * Frees up the memory associated with this region decoder, and mark the region decoder as "dead",
     * meaning it will throw an exception if decodeRegion(), getWidth() or getHeight() is called.
     */
    @Override
    public void recycle() {
        impl.recycle();
    }
}
