package com.jep.github.classloader;

public class HeapArgsTest {

  public static void main(String[] args) {

    try {
      Thread.sleep(100000000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
}