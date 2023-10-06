package com.jep.github.interview.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author enping.jep
 * @date 2023/09/25 19:19
 **/
@Controller
public class TestController {

    @RequestMapping(value = "/test")
    public String test1(HttpServletRequest request) {
        List<Byte[]> temp = new ArrayList<Byte[]>();
        Byte[] b = new Byte[1024 * 1024];
        temp.add(b);
        return "success";
    }
}
