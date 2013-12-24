package org.hibernatedao.test;

import org.hibernatedao.assist.Condition;
import org.hibernatedao.core.Dao;
import org.hibernatedao.pojo.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Nesson on 12/19/13.
 */

@Transactional
@Controller
public class MainModule {

    @Resource(name="dao")
    Dao dao;

    @ResponseBody
    @RequestMapping({"/create", "/"})
    public String create(){

        for(int i = 0; i < 100; i ++){

            Pet pet = new Pet();

            pet.setName(Integer.toString(i));

            dao.insert(pet);
        }

        return "success";
    }

    @ResponseBody
    @RequestMapping("/count")
    public String count(){
        return dao.count(Pet.class).toString();
    }

    @ResponseBody
    @RequestMapping("/clear")
    public String clear(){
        dao.clear(Pet.class);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/last")
    public String lastPet(){
        Condition cnd = new Condition().desc("id");
        List<Pet> petList = dao.query(Pet.class, cnd);
        return petList.get(petList.size()-1).getName();
    }

    @ResponseBody
    @RequestMapping("/create+hummer")
    public String createHummer(){
        Pet pet = new Pet();
        pet.setName("hummer");
        dao.insert(pet);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/hummer")
    public String getPetByName(){
        Pet pet = dao.fetch(Pet.class, "hummer");
        return pet.getName()+ ", " + pet.getId();
    }

}
