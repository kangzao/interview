package com.jep.github.interview.concurrency.threadDemo.chain;

import com.jep.github.interview.concurrency.threadDemo.chain.Request;

public interface IRequestProcessor {

  void process(Request request);
}