package com.gigigo.orchextra.ocm.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.gigigo.orchextra.core.controller.views.UiBaseContentData;
import com.gigigo.orchextra.core.sdk.model.grid.dto.ClipToPadding;
import com.gigigo.orchextra.ocm.callbacks.OnLoadMoreContentListener;

public abstract class UiGridBaseContentData extends UiBaseContentData {

  protected OnLoadMoreContentListener onLoadMoreContentListener;

  public abstract void setFilter(String filter);

  public abstract void setClipToPaddingBottomSize(ClipToPadding clipToPadding);

  public abstract void scrollToTop();

  public abstract void setEmptyView(View emptyView);

  public abstract void setErrorView(View errorLayoutView);

  public abstract void setProgressView(View progressView);

  public abstract void reloadSection();

  public void setOnLoadMoreContentListener(OnLoadMoreContentListener onLoadMoreContentListener) {
    this.onLoadMoreContentListener = onLoadMoreContentListener;
  }
}
