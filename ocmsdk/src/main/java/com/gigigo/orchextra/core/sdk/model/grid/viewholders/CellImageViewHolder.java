package com.gigigo.orchextra.core.sdk.model.grid.viewholders;

import android.content.Context;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;
import com.gigigo.orchextra.core.controller.dto.CellGridContentData;
import com.gigigo.orchextra.core.data.rxCache.imageCache.loader.OcmImageLoader;
import com.gigigo.orchextra.core.domain.entities.elements.ElementSectionView;
import com.gigigo.orchextra.core.domain.entities.menus.RequiredAuthoritation;
import com.gigigo.orchextra.core.domain.entities.ocm.Authoritation;
import com.gigigo.orchextra.core.sdk.utils.ImageGenerator;
import com.gigigo.orchextra.ocmsdk.R;

public class CellImageViewHolder extends BaseViewHolder<CellGridContentData> {

  private final View mainLayout;
  private final View padlockView;
  private final Context context;
  private final boolean thumbnailEnabled;

  private ImageView imageView;
  private Authoritation authoritation;

  public CellImageViewHolder(Context context, ViewGroup parent, Authoritation authoritation,
      boolean thumbnailEnabled) {
    super(context, parent, R.layout.cell_image_content_item);

    this.context = context;
    this.authoritation = authoritation;
    this.thumbnailEnabled = thumbnailEnabled;

    imageView = (ImageView) itemView.findViewById(R.id.cell_image_view);
    padlockView = itemView.findViewById(R.id.padlock_layout);
    mainLayout = itemView.findViewById(R.id.cell_image_content_layout);
  }

  @Override public void bindTo(CellGridContentData item, final int position) {
    final ElementSectionView sectionView = item.getData().getSectionView();

    if (sectionView != null) {

      mainLayout.getViewTreeObserver()
          .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override public boolean onPreDraw() {
              byte[] imageByteArray = Base64.decode(sectionView.getImageThumb(), Base64.DEFAULT);

              String generatedImageUrl =
                  ImageGenerator.generateImageUrl(sectionView.getImageUrl(), mainLayout.getWidth(),
                      mainLayout.getHeight());

              DrawableRequestBuilder<String> requestBuilder =
                  OcmImageLoader.load(context, generatedImageUrl)
                      .priority(Priority.NORMAL)
                      .dontAnimate();

              if (thumbnailEnabled) {
                requestBuilder = requestBuilder.thumbnail(Glide.with(context).load(imageByteArray));
              }

              requestBuilder.into(imageView);

              mainLayout.getViewTreeObserver().removeOnPreDrawListener(this);

              return true;
            }
          });
    }

    if (item.getData().getSegmentation().getRequiredAuth().equals(RequiredAuthoritation.LOGGED)) {
      padlockView.setVisibility(authoritation.isAuthorizatedUser() ? View.GONE : View.VISIBLE);
    } else {
      padlockView.setVisibility(View.GONE);
    }
  }
}
