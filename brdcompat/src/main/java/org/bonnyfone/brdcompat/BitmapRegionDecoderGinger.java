package org.bonnyfone.brdcompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Backward-compatible implementation of the BitmapRegionDecoder API
 */
class BitmapRegionDecoderGinger implements IBitmapRegionDecoder {

    private Object encodedImage;
    private BitmapFactory.Options bitmapOptions;

    private int offset = -1;
    private int length = -1;
    private int width = -1;
    private int height = -1;
    private boolean isRecycled;
    private boolean decoderIsSharable;

    private BitmapRegionDecoderGinger(){}

    private BitmapRegionDecoderGinger(String pathName, boolean isShareable) throws IOException {
        encodedImage = pathName;
        init(isShareable);
        BitmapFactory.decodeFile(pathName, bitmapOptions);
        checkInit();
    }

    private BitmapRegionDecoderGinger(FileDescriptor fd, boolean isShareable) throws IOException {
        encodedImage = fd;
        init(isShareable);
        BitmapFactory.decodeFileDescriptor(fd, null, bitmapOptions);
        checkInit();
    }

    private BitmapRegionDecoderGinger(byte[] data, int offset, int length, boolean isShareable) throws IOException {
        encodedImage = data;
        this.offset = offset;
        this.length = length;
        init(isShareable);
        BitmapFactory.decodeByteArray(data, offset, length, bitmapOptions);
        checkInit();
    }

    private BitmapRegionDecoderGinger(InputStream is, boolean isShareable) throws IOException {
        encodedImage = is;
        init(isShareable);
        BitmapFactory.decodeStream(is, null, bitmapOptions);
        checkInit();
    }

    private void init(boolean isInputSharable){
        decoderIsSharable = isInputSharable;
        bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        bitmapOptions.inInputShareable = isInputSharable;
    }

    private void checkInit() throws IOException {
        width = bitmapOptions.outWidth;
        height = bitmapOptions.outHeight;

        if(width == -1 || height == -1){
            throw new IOException("Unable to decode image bounds.");
        }
    }

    /**
     * Fallback decode-region method
     * @param encodedImage
     * @param area
     * @param opts
     * @return
     */
    private Bitmap fallbackDecode(Object encodedImage, Rect area, BitmapFactory.Options opts) {
        int rectFactor = 1;
        if(opts != null){
            rectFactor = opts.inSampleSize >= 1 ? opts.inSampleSize : 1;
            //TODO more cases here
        }

        return Bitmap.createBitmap(decodeObjectToBitmap(encodedImage, opts), area.left/rectFactor, area.top/rectFactor, area.right/rectFactor - area.left/rectFactor, area.bottom/rectFactor - area.top/rectFactor);
    }

    private Bitmap decodeObjectToBitmap(Object encodedImage, BitmapFactory.Options opts){
        if(encodedImage instanceof byte[]){
            return BitmapFactory.decodeByteArray((byte[])encodedImage, offset, length, opts);
        }
        else if(encodedImage instanceof InputStream){
            return BitmapFactory.decodeStream((InputStream)encodedImage, null, opts);
        }
        else if(encodedImage instanceof String){
            return BitmapFactory.decodeFile((String)encodedImage, opts);
        }
        else if(encodedImage instanceof  FileDescriptor){
            return BitmapFactory.decodeFileDescriptor((FileDescriptor)encodedImage, null, opts);
        }
        else
            return null;
    }

    @Override
    public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options) {
        return fallbackDecode(encodedImage, rect, options);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public boolean isRecycled() {
        return isRecycled;
    }

    @Override
    public void recycle() {
        isRecycled = true;
        bitmapOptions = null;
        encodedImage = null;
        width = -1;
        height = -1;
        offset = -1;
        length = -1;
    }

    public static BitmapRegionDecoderGinger newInstance(String pathName, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderGinger(pathName, isShareable);
    }

    public static BitmapRegionDecoderGinger newInstance(FileDescriptor fd, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderGinger(fd, isShareable);
     }

    public static BitmapRegionDecoderGinger newInstance(byte[] data, int offset, int length, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderGinger(data, offset, length, isShareable);
     }

    public static BitmapRegionDecoderGinger newInstance(InputStream is, boolean isShareable) throws IOException {
        return new BitmapRegionDecoderGinger(is, isShareable);
    }

}
