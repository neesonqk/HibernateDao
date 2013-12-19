package org.hibernatedao.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Nesson on 12/19/13.
 */
@Controller
public class MainModule {

    @ResponseBody
    @RequestMapping({"/index", "/"})
    public String index(){
        return "Hello World";
    }

}
