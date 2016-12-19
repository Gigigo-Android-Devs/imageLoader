package com.gigigo.ui.imageloader.picasso;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.gigigo.ui.imageloader.ImageLoader;
import com.gigigo.ui.imageloader.ImageLoaderCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class PicassoImageLoaderImp implements ImageLoader {

  private final Context context;
  private final Picasso picasso;

  private int resourceId;
  private String url;

  private Drawable placeholder;
  private Drawable error;

  private int width;
  private int height;

  private ImageLoaderCallback imageLoaderCallback;

  private Transformation bitmapTransformation;

  private ImageView imageview;

  public PicassoImageLoaderImp(Context context) {
    this.context = context;
    this.picasso = Picasso.with(context);
  }

  public PicassoImageLoaderImp(Context context, Transformation bitmapTransformation) {
    this.context = context;
    this.picasso = Picasso.with(context);
    this.bitmapTransformation = bitmapTransformation;
  }

  @Override public ImageLoader load(int resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  @Override public ImageLoader load(String url) {
    this.url = url;
    return this;
  }

  @Override public ImageLoader into(ImageView imageView) {
    this.imageview = imageView;
    return this;
  }

  @Override public ImageLoader placeholder(int placeholder) {
    placeholder(context.getResources().getDrawable(placeholder));
    return this;
  }

  @Override public ImageLoader placeholder(Drawable placeholder) {
    this.placeholder = placeholder;
    return this;
  }

  @Override public ImageLoader error(int error) {
    error(context.getResources().getDrawable(error));
    return this;
  }

  @Override public ImageLoader error(Drawable error) {
    this.error = error;
    return this;
  }

  @Override public ImageLoader override(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  @Override public ImageLoader loaderCallback(ImageLoaderCallback imageLoaderCallback) {
    this.imageLoaderCallback = imageLoaderCallback;
    return this;
  }

  @Override public void build() {
    RequestCreator requestCreator;

    if (!TextUtils.isEmpty(url)) {
      requestCreator = picasso.load(url);
    } else if (!TextUtils.isEmpty(url)) {
      requestCreator = picasso.load(resourceId);
    } else {
      return;
    }

    if (placeholder != null) {
      requestCreator = requestCreator.placeholder(placeholder);
    }

    if (error != null) {
      requestCreator = requestCreator.error(error);
    }

    if (width > 0 && height > 0) {
      requestCreator = requestCreator.resize(width, height);
    }

    if (bitmapTransformation != null) {
      requestCreator = requestCreator.transform(bitmapTransformation);
    }

    if (imageview != null) {
      if (imageLoaderCallback != null) {
        requestCreator.into(imageview, new Callback() {
          @Override public void onSuccess() {
            imageLoaderCallback.onFinish(true);
          }

          @Override public void onError() {
            imageLoaderCallback.onFinish(false);
          }
        });
      } else {
        requestCreator.into(imageview);
      }
    }
  }
}