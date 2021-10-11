package com.jep.github.demo.action;

import com.jep.github.mvc.annotation.ManualAutowired;
import com.jep.github.mvc.annotation.ManualController;
import com.jep.github.mvc.annotation.ManualRequestMapping;
import com.jep.github.mvc.annotation.ManualRequestParam;
import com.jep.github.demo.service.IDemoService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//虽然，用法一样，但是没有功能
@ManualController
@ManualRequestMapping("/demo")
public class DemoAction {

  @ManualAutowired
  private IDemoService demoService;

  @ManualRequestMapping("/query.*")
  public void query(HttpServletRequest req, HttpServletResponse resp,
      @ManualRequestParam("name") String name) {
//		String result = demoService.get(name);
    String result = "My name is " + name;
    try {
      resp.getWriter().write(result);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @ManualRequestMapping("/add")
  public void add(HttpServletRequest req, HttpServletResponse resp,
      @ManualRequestParam("a") Integer a, @ManualRequestParam("b") Integer b) {
    try {
      resp.getWriter().write(a + "+" + b + "=" + (a + b));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @ManualRequestMapping("/sub")
  public void add(HttpServletRequest req, HttpServletResponse resp,
      @ManualRequestParam("a") Double a, @ManualRequestParam("b") Double b) {
    try {
      resp.getWriter().write(a + "-" + b + "=" + (a - b));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @ManualRequestMapping("/remove")
  public String remove(@ManualRequestParam("id") Integer id) {
    return "" + id;
  }

}