package com.jep.github.mvc.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @author: enping.jep
 * @create: 2021-10-09 5:05 下午
 */
@WebServlet(name = "MyServlet", urlPatterns = {"/myServlet"})
public class MyServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.getWriter().println("<html>\n" +
        "<body>\n" +
        "<h2>Hello World MyServlet xxx</h2>\n" +
        "</body>\n" +
        "</html>\n");
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    this.doPost(request, response);
  }
}

