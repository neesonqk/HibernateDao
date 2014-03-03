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

        Condition c = Cnd.where("id", ">", 250).desc("id");

        //List<Pet> pets = dao.query(Pet.class, c);

        //Pet pet = dao.fetch(Pet.class, Cnd.where("id", ">", 250).and("name", "=", "99"));

        List<Pet> pets1 = dao.query(Pet.class, Cnd.where("id", ">", 250).or("id", "<", 170).or("name","=","15").and("name","=","99"));

        List<Pet> pets2 = dao.query(Pet.class, Cnd.where("name", "like", "9%"));

        List<Pet> pets3 = dao.query(Pet.class, Cnd.where("name", "not in", new String[]{}).desc("id"));

        Condition condition = Cnd.where("id", ">", 200).and("id", "<", 220);

        Condition condition1 = Cnd.where("id", ">", 250).and("id", "<", 260);

        Condition condition2 = Cnd.where(condition).or(condition1);

        List<Pet> pets = dao.query(Pet.class, condition2);

        return "success";
    }


}
