package com.jep.github.interview.concurrency.threadDemo.chain;

public class Request {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Request{" +
        "name='" + name + '\'' +
        '}';
  }
}