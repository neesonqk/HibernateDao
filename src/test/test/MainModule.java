package test.test;


import org.hibernatedao.core.Dao;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import test.test.pojo.Master;

import javax.annotation.Resource;


/**
 * Created by Nesson on 12/19/13.
 */

@Transactional
@Controller
public class MainModule {

    @Resource(name = "dao")
    Dao dao;

    @RequestMapping({"/create", "/", "/index"})
    public ModelAndView Test() {

        ModelAndView mv = new ModelAndView();

        Master master = dao.fetch(Master.class, 1);

        mv.addObject("master", master);
        mv.setViewName("/test");
        return mv;
    }

}
