package com.gigigo.imageloader_demoapp;

import java.util.Random;

public class DataGenerator {

  private static String[] categories =
      new String[] { "animals", "architecture", "nature", "people", "tech" };

  public static String generateRandomImageUrl() {
    StringBuilder url = new StringBuilder("http://placeimg.com/300/200/");
    //TODO: try with "http://lorempixel.com/200/100/" for testing SocketTimeoutException
    return  url.append(getCategory()).toString();
  }

  private static String getCategory() {
    int random = new Random().nextInt();
    int index = Math.abs(random) % (categories.length - 1);
    return categories[index];
  }
}