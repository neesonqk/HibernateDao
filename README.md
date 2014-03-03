HibernateDao
=========

HibernateDao is a tool library for Hibernate & SpringMVC, which facilities application retrives data from database.  

 >"you can retrive data from database like this"

 ```
 //Create condition 
 Condition c = Cnd.where("name", "=", "HibernateDao");
 
 //Get the object
 Pet pet = dao.fetch(Pet.class, c);
 
 ```

Version
----
  - 1.0

Installation
-----------
>To use HibernateDao you have to follow below steps(annotation style)

- **SpringMVC & Hibernate libraries** - add SpringMVC & Hibernate libraries into your project(you can find all necessary libraries from this downloaded project( WEB-INF/lib/).
- **HibernateDao library** - add HiernateDao library( HibernateDao.jar).
- **SpringMVC Configruation**<br />
```
    <context:component-scan base-package="org.hibernatedao, ..." />
```
- **SpringMVC SessionFactory Bean** - bean's name has to be '**sessionFactory**'<br />
```
    <bean id="sessionFactory" 
        class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
        <property name="packagesToScan" value="your.pojo.class.package" />
        <property name="dataSource" ref="dataSource"/><!-your DataSource Bean reference-->
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
            </props>
        </property>
    </bean>
```
- **Done**

How to use it
-----------

> Inject Dao into your class like this 

```
@Controller
public class Tester {

    @Resource(name="dao")
	private Dao dao;
	
	@ResponseBody
	@RequestMapping("/")
	public String test(){
		
		Condition c = Cnd.where("name", "=", "99");
		
		Pet pet = dao.fetch(Pet.class, c);
		
		return pet.getName() + ", " + pet.getId();
	}
}
```

Examples
---

```
Condition c = Cnd.where("id", ">", 250).desc("id");

        List<Pet> pets = dao.query(Pet.class, c);

        Pet pet = dao.fetch(Pet.class, Cnd.where("id", ">", 250).and("name", "=", "99"));


        //dao.delete(pet);

        dao.fetch(Pet.class, "99");// String primary key, use @Name and @Column(unique = true) annotate class field.

        List<Pet> pets1 = dao.query(Pet.class, Cnd.where("id", ">", 250).or("id", "<", 170).or("name","=","15").and("name","=","99"));

        List<Pet> pets2 = dao.query(Pet.class, Cnd.where("name", "like", "9%"));

        List<Pet> pets3 = dao.query(Pet.class, Cnd.where("name", "not in", new String[]{"77","88"}).desc("id"));

        Condition condition = Cnd.where("id", ">", 200).and("id", "<", 220);

        Condition condition1 = Cnd.where("id", ">", 250).and("id", "<", 260);

        Condition condition2 = Cnd.where(condition).or(condition1);

        List<Pet> pets22 = dao.query(Pet.class, condition2);

        return "success";
```


License
----

MIT


**Free Software, Hell Yeah!**


    