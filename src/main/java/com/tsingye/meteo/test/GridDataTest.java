package com.tsingye.meteo.test;

import java.util.Arrays;

public class GridDataTest {

  public static void main(String[] args) {
    System.out.println("args below:");
    Arrays.stream(args).forEach(System.out::println);
    System.out.println("system properties below");
    System.getProperties().forEach((k, v) -> System.out.println(k + "=" + v));
  }

}
