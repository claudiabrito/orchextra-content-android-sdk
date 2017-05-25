package com.gigigo.orchextra.core.data.rxCache;

import android.content.Context;
import com.gigigo.orchextra.core.sdk.di.qualifiers.CacheDir;
import com.mskn73.kache.Kache;
import javax.inject.Named;
import orchextra.javax.inject.Inject;
import orchextra.javax.inject.Singleton;

/**
 * Created by francisco.hernandez on 23/5/17.
 */

@Singleton public class OcmKacheImp implements OcmCache {

  private final Kache kache;

  @Inject public OcmKacheImp(Context context, @CacheDir String cacheDir) {
    this.kache = new Kache(context, cacheDir);
  }
}
