package com.gigigo.ui.imageloader.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.gigigo.ui.imageloader.ImageLoaderBuilder;
import com.gigigo.ui.imageloader.ImageLoaderCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;

class ImageLoaderBuilderImp implements ImageLoaderBuilder {

  private final Context context;
  private final Picasso picasso;

  int resourceId;
  String url;

  private Drawable placeholder;
  private Drawable error;

  private int width;
  private int height;

  private float degrees;

  private ImageLoaderCallback imageLoaderCallback;

  private ArrayList<Transformation> bitmapTransformation;

  private boolean centerCrop;

  private boolean fitCenter;

  private ImageView imageview;

  public ImageLoaderBuilderImp(Context context) {
    this.context = context;
    this.picasso = Picasso.with(context);
  }

  //@Override public ImageLoaderBuilder into(ImageView imageView) {
  //  this.imageview = imageView;
  //  return this;
  //}

  @Override public ImageLoaderBuilder placeholder(int placeholder) {
    placeholder(context.getResources().getDrawable(placeholder));
    return this;
  }

  @Override public ImageLoaderBuilder placeholder(Drawable placeholder) {
    this.placeholder = placeholder;
    return this;
  }

  @Override public ImageLoaderBuilder error(int error) {
    error(context.getResources().getDrawable(error));
    return this;
  }

  @Override public ImageLoaderBuilder error(Drawable error) {
    this.error = error;
    return this;
  }

  @Override public ImageLoaderBuilder override(int width, int height) {
    this.width = width;
    this.height = height;
    return this;
  }

  @Override public ImageLoaderBuilder transform(Object... bitmapTransformations) {
    bitmapTransformation = new ArrayList<>();
    for (int i = 0; i < bitmapTransformations.length; i++) {
      if (bitmapTransformations[i] instanceof Transformation) {
        bitmapTransformation.add((Transformation) bitmapTransformations[i]);
      }
    }
    return this;
  }

  //@Override public ImageLoaderBuilder loaderCallback(ImageLoaderCallback imageLoaderCallback) {
  //  this.imageLoaderCallback = imageLoaderCallback;
  //  return this;
  //}

  @Override public ImageLoaderBuilder centerCrop(Boolean centerCrop) {
    this.centerCrop = centerCrop;
    return this;
  }

  @Override public ImageLoaderBuilder fitCenter(Boolean fitCenter) {
    this.fitCenter = fitCenter;
    return this;
  }

  @Override public ImageLoaderBuilder rotate(float degrees) {
    this.degrees = degrees;
    return this;
  }

  @Override public ImageLoaderBuilder animate(Boolean animate) {
    return null;
  }

  @Override public ImageLoaderBuilder sizeMultiplier(float sizeMultiplier) {
    return null;
  }

  @Override public void into(ImageView imageView) {
    into(null, imageView);
  }

  @Override public void into(final ImageLoaderCallback imageLoaderCallback) {
    into(imageLoaderCallback, null);
  }

  @Override
  public void into(final ImageLoaderCallback imageLoaderCallback, final ImageView imageView) {

    if (imageView != null) {
      this.imageview = imageView;
      RequestCreator requestCreator = build();
      requestCreator.into(imageView);
    } else {
      RequestCreator requestCreator = build();
      requestCreator.into(new Target() {

        @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
          if (imageView != null) {
            imageView.setImageBitmap(bitmap);
          }
          imageLoaderCallback.onSuccess(bitmap);
        }

        @Override public void onBitmapFailed(Drawable errorDrawable) {
          imageLoaderCallback.onError(errorDrawable);
        }

        @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
          imageLoaderCallback.onLoading();
        }
      });
    }
  }

  private RequestCreator build() {
    RequestCreator requestCreator;

    if (!TextUtils.isEmpty(url)) {
      requestCreator = picasso.load(url);
    } else if (resourceId != 0) {
      requestCreator = picasso.load(resourceId);
    } else {
      return null;
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
      for (int i = 0; i < bitmapTransformation.size(); i++) {
        requestCreator = requestCreator.transform(bitmapTransformation.get(i));
      }
    }

    if (centerCrop) {
      requestCreator = requestCreator.centerCrop();
    }

    if (fitCenter) {
      requestCreator = requestCreator.centerInside();
    }
    if (degrees > 0) {
      requestCreator = requestCreator.rotate(degrees);
    }

    return requestCreator;
  }

  @Override public void clearPreviousData() {
    resourceId = 0;
    url = null;

    placeholder = null;
    error = null;

    width = 0;
    height = 0;

    imageLoaderCallback = null;

    bitmapTransformation = null;

    imageview = null;

    centerCrop = false;

    fitCenter = false;

    degrees = 0;
  }
}
