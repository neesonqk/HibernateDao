package test.test;

import org.hibernatedao.assist.Cnd;
import org.hibernatedao.assist.Condition;
import org.hibernatedao.core.Dao;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import test.test.pojo.Pet;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * Created by Nesson on 12/19/13.
 */

@Transactional
@Controller
public class MainModule {

    @Resource(name = "dao")
    Dao dao;

    @RequestMapping({"/create", "/", "/index"})
    public @ResponseBody String Test() {

        Condition c = Cnd.where("id", ">", 650).desc("id");

        Condition d = Cnd.where("id", "<", 760);

        Condition e = Cnd.where(c).and(d);

        List<Pet> pets = dao.query(Pet.class, e);

        return "success";
    }

}
