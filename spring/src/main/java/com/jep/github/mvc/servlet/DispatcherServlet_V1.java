package com.jep.github.mvc.servlet;

import com.jep.github.mvc.annotation.ManualAutowired;
import com.jep.github.mvc.annotation.ManualController;
import com.jep.github.mvc.annotation.ManualRequestMapping;
import com.jep.github.mvc.annotation.ManualService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet_V1 extends HttpServlet {

  private Map<String, Object> mapping = new HashMap<String, Object>();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    this.doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {

      doDispatch(req, resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    String url = req.getRequestURI();
    String contextPath = req.getContextPath();
    url = url.replace(contextPath, "").replaceAll("/+", "/");
    if (!this.mapping.containsKey(url)) {
      resp.getWriter().write("404 Not Found!!");
      return;
    }
    Method method = (Method) this.mapping.get(url);
    Map<String, String[]> params = req.getParameterMap();
    method.invoke(this.mapping.get(method.getDeclaringClass().getName()),
        new Object[]{req, resp, params.get("id")[0]});
  }

  //当我晕车的时候，我就不去看源码了

  //init方法肯定干得的初始化的工作
  //inti首先我得初始化所有的相关的类，IOC容器、servletBean
  @Override
  public void init(ServletConfig config) throws ServletException {
    InputStream is = null;
    try {
      Properties configContext = new Properties();
      is = this.getClass().getClassLoader()
          .getResourceAsStream(config.getInitParameter("contextConfigLocation"));
      configContext.load(is);
      String scanPackage = configContext.getProperty("scanPackage");
      doScanner(scanPackage);
      for (String className : mapping.keySet()) {
        if (!className.contains(".")) {
          continue;
        }
        Class<?> clazz = Class.forName(className);
        if (clazz.isAnnotationPresent(ManualController.class)) {
          mapping.put(className, clazz.newInstance());
          String baseUrl = "";
          if (clazz.isAnnotationPresent(ManualRequestMapping.class)) {
            ManualRequestMapping requestMapping = clazz.getAnnotation(ManualRequestMapping.class);
            baseUrl = requestMapping.value();
          }
          Method[] methods = clazz.getMethods();
          for (Method method : methods) {
            if (!method.isAnnotationPresent(ManualRequestMapping.class)) {
              continue;
            }
            ManualRequestMapping requestMapping = method.getAnnotation(ManualRequestMapping.class);
            String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
            mapping.put(url, method);
            System.out.println("Mapped " + url + "," + method);
          }
        } else if (clazz.isAnnotationPresent(ManualService.class)) {
          ManualService service = clazz.getAnnotation(ManualService.class);
          String beanName = service.value();
          if ("".equals(beanName)) {
            beanName = clazz.getName();
          }
          Object instance = clazz.newInstance();
          mapping.put(beanName, instance);
          for (Class<?> i : clazz.getInterfaces()) {
            mapping.put(i.getName(), instance);
          }
        } else {
          continue;
        }
      }
      for (Object object : mapping.values()) {
        if (object == null) {
          continue;
        }
        Class clazz = object.getClass();
        if (clazz.isAnnotationPresent(ManualController.class)) {
          Field[] fields = clazz.getDeclaredFields();
          for (Field field : fields) {
            if (!field.isAnnotationPresent(ManualAutowired.class)) {
              continue;
            }
            ManualAutowired autowired = field.getAnnotation(ManualAutowired.class);
            String beanName = autowired.value();
            if ("".equals(beanName)) {
              beanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {
              field.set(mapping.get(clazz.getName()), mapping.get(beanName));
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
          }
        }
      }
    } catch (Exception e) {
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.print("Manual MVC Framework is init");
  }

  private void doScanner(String scanPackage) {
    URL url = this.getClass().getClassLoader()
        .getResource("/" + scanPackage.replaceAll("\\.", "/"));
    File classDir = new File(url.getFile());
    for (File file : classDir.listFiles()) {
      if (file.isDirectory()) {
        doScanner(scanPackage + "." + file.getName());
      } else {
        if (!file.getName().endsWith(".class")) {
          continue;
        }
        String clazzName = (scanPackage + "." + file.getName().replace(".class", ""));
        mapping.put(clazzName, null);
      }
    }
  }
}