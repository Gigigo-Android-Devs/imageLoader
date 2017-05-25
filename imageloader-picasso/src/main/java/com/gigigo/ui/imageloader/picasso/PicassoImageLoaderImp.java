package com.gigigo.ui.imageloader.picasso;

import android.content.Context;
import android.view.View;
import com.gigigo.ui.imageloader.ImageLoader;
import com.gigigo.ui.imageloader.ImageLoaderBuilder;
import com.squareup.picasso.Picasso;

public class PicassoImageLoaderImp extends ImageLoaderBuilderImp implements ImageLoader {
  Context context;

  public PicassoImageLoaderImp(Context context) {
    super(context);
  }

  @Override public ImageLoaderBuilder load(int resourceId) {
    clearPreviousData();
    this.resourceId = resourceId;
    return this;
  }

  @Override public ImageLoaderBuilder load(String url) {
    clearPreviousData();
    this.url = url;
    return this;
  }

  @Override public void pauseRequests() {
    Picasso.with(context).pauseTag(context);

  }

  @Override public void resumeRequests() {
    Picasso.with(context).resumeTag(context);

  }

  @Override public void clear(View view) {

  }
}
