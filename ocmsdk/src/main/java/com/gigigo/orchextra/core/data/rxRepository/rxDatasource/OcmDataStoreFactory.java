package com.gigigo.orchextra.core.data.rxRepository.rxDatasource;

import android.util.Log;
import com.gigigo.orchextra.core.data.rxCache.OcmCache;
import orchextra.javax.inject.Inject;
import orchextra.javax.inject.Named;
import orchextra.javax.inject.Singleton;

/**
 * Created by francisco.hernandez on 23/5/17.
 */

@Singleton public class OcmDataStoreFactory {

  private static final String TAG = OcmDataStoreFactory.class.getSimpleName();

  private final OcmCloudDataStore cloudDataStore;
  private final OcmDiskDataStore diskDataStore;

  @Inject public OcmDataStoreFactory(OcmCloudDataStore cloudDataStore,
      OcmDiskDataStore diskDataStore) {
    this.cloudDataStore = cloudDataStore;
    this.diskDataStore = diskDataStore;
  }

  public OcmDataStore getDataStoreForMenus(boolean force) {
    OcmDataStore ocmDataStore;

    if (force) {
      Log.i(TAG, "CLOUD - Menus");
      ocmDataStore = getCloudDataStore();
    } else {
      OcmCache cache = diskDataStore.getOcmCache();
      if (cache.isMenuCached() && !cache.isMenuExpired()) {
        Log.i(TAG, "DISK  - Menus");
        ocmDataStore = getDiskDataStore();
      } else {
        Log.i(TAG, "CLOUD - Menus");
        ocmDataStore = getCloudDataStore();
      }
    }

    return ocmDataStore;
  }

  public OcmDataStore getDataStoreForSections(boolean force, String section) {
    OcmDataStore ocmDataStore;

    if (force) {
      Log.i(TAG, "CLOUD - Sections");
      ocmDataStore = getCloudDataStore();
    } else {
      OcmCache cache = diskDataStore.getOcmCache();
      if (cache.isSectionCached(section) && !cache.isSectionExpired(section)) {
        Log.i(TAG, "DISK  - Sections");
        ocmDataStore = getDiskDataStore();
      } else {
        Log.i(TAG, "CLOUD - Sections");
        ocmDataStore = getCloudDataStore();
      }
    }

    return ocmDataStore;
  }


  public OcmDataStore getDataStoreForDetail(boolean force, String slug) {
    OcmDataStore ocmDataStore;

    if (force) {
      Log.i(TAG, "CLOUD - Detail");
      ocmDataStore = getCloudDataStore();
    } else {
      OcmCache cache = diskDataStore.getOcmCache();
      if (cache.isDetailCached(slug) && !cache.isDetailExpired(slug)) {
        Log.i(TAG, "DISK  - Detail");
        ocmDataStore = getDiskDataStore();
      } else {
        Log.i(TAG, "CLOUD - Detail");
        ocmDataStore = getCloudDataStore();
      }
    }

    return ocmDataStore;
  }


  public OcmDataStore getCloudDataStore() {
    return cloudDataStore;
  }

  public OcmDataStore getDiskDataStore() {
    return diskDataStore;
  }
}