package com.riversouther.HelloSpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @auther chao
 * @date 16/4/23
 */
@Controller
public class HelloController {

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public String showHomePage(Map<String, Object> model) {
        //model.put("content", "Hello Spring");
        return "home";
    }
}
