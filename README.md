# BitmapRegionDecoderCompat

**BitmapRegionDecoderCompat** (*BRDCompat*) is the 'compat' version of the official [BitmapRegionDecoder] API. 

 > *BitmapRegionDecoder* can be used to decode a rectangle region from an image; it is particularly useful when an original image is large and you only need parts of the image. 

The standard *BitmapRegionDecoder* (aka *BRD*) requires API level 10. 

**BRDCompat extends the compatibility down to API 8 and adds some extra useful methods**.



 Features
----

   
 - Extends compatibility down to **API 8** (from 10)
 - Adds new methods like **decodeBestRegion()**, which extracts the *best* image sub-region given the specified params (required size, gravity). The best region means the biggest visual portion of the (downsampled) original image given the required gravity and the output sizes/ratio.

Download
----
### Manual download (AAR, sources, javadoc)
[ ![Download](https://api.bintray.com/packages/bonnyfone/maven/org.bonnyfone.brdcompat/images/download.svg) ](https://bintray.com/bonnyfone/maven/org.bonnyfone.brdcompat/_latestVersion)

### Gradle dependency
Add the following 'compile' statement in your **build.gradle** file (requires *jCenter* repository):
```groovie
dependencies {
    //...your dependecies
    compile 'org.bonnyfone:brdcompat:0.1'
}
```

 How To...
----
 
 
### ...upgrade from the standard API

If you are already using the *BitmapRegionDecoder* API, just change the base class name from *BitmapRegionDecoder* to *BitmapRegionDecoderCompat*.

```java
//BitmapRegionDecoder brd = BitmapRegionDecoder.newInstance(...);
BitmapRegionDecoderCompat brd = BitmapRegionDecoderCompat.newInstance(...);
```


### ...simulate the fallback for API < 10 on device/emulator running API 10+
**For debug purpose only** you can force the library to work in backward-compatibility mode by invoking the following static method (be sure to do this before the creation of any instance of BRDCompat):
```java
BitmapRegionDecoderCompat.setForceFallbackImplementation(true);
```

Example
----
Check out the [demo] for a simple usage example.

![result example](http://s9.postimg.org/afr5dt1mn/brd.png)

Contribute
----
Bug-reports, feedback and pull requests are always welcome ;)

License
----

```
Copyright 2015, Stefano Bonetta.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[BitmapRegionDecoder]:http://developer.android.com/reference/android/graphics/BitmapRegionDecoder.html
[demo]:https://github.com/bonnyfone/brdcompat_demo
