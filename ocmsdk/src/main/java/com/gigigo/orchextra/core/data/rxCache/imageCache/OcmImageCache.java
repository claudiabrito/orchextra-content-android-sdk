package com.gigigo.orchextra.core.data.rxCache.imageCache;

/**
 * Created by francisco.hernandez on 6/6/17.
 */

public interface OcmImageCache {

  void downloadImage(String url, int priority);
}