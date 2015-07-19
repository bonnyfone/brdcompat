package org.bonnyfone.brdcompat;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Internal wrapper over the native API, allows to maintain type interoperability
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class BitmapRegionDecoderNative implements IBitmapRegionDecoder {

    private android.graphics.BitmapRegionDecoder nativeDecoder;

    private BitmapRegionDecoderNative(android.graphics.BitmapRegionDecoder nativeDecoder){
        this.nativeDecoder = nativeDecoder;
    }

    @Override
    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        return nativeDecoder.decodeRegion(rect, options);
    }

    @Override
    public int getHeight() {
        return nativeDecoder.getHeight();
    }

    @Override
    public int getWidth() {
        return nativeDecoder.getWidth();
    }

    @Override
    public boolean isRecycled() {
        return nativeDecoder.isRecycled();
    }

    @Override
    public void recycle() {
        nativeDecoder.recycle();
    }

    public static BitmapRegionDecoderNative newInstance(String pathName, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderNative(android.graphics.BitmapRegionDecoder.newInstance(pathName, isShareable));
    }

    public static BitmapRegionDecoderNative newInstance(FileDescriptor fd, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderNative(android.graphics.BitmapRegionDecoder.newInstance(fd, isShareable));
    }

    public static BitmapRegionDecoderNative newInstance(byte[] data, int offset, int length, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderNative(android.graphics.BitmapRegionDecoder.newInstance(data, offset, length, isShareable));
    }

    public static BitmapRegionDecoderNative newInstance(InputStream is, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderNative(android.graphics.BitmapRegionDecoder.newInstance(is, isShareable));
    }
}
