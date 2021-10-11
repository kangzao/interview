package com.jep.github.demo.service.impl;

import com.jep.github.mvc.annotation.ManualService;
import com.jep.github.demo.service.IDemoService;

/**
 * 核心业务
 */
@ManualService
public class DemoService implements IDemoService {

  public String get(String name) {
    return "My name is " + name;
  }

}