HibernateDao
============

Packaged Hibernate Dao layout for springMVC

<b>Description:</b>
HibernateDao facilitate you on reading and writing data with database.

<b>Dependence</b>
1. SpringMVC
2. Hibernate3

<b>How to use</b>
1. Create sessionFactory bean within SpringMVC configuration file

<bean id="sessionFactory" class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
        <property name="packagesToScan" value="org.hibernatedao.pojo" /> <!-- change to your pojo package -->
        <property name="dataSource" ref="dataSource"/> <!-- refer to your datasource bean -->
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop> <!-- auto generate table -->
            </props>
        </property>
</bean>

2. Add context scanner

 <context:component-scan base-package="org.hibernatedao" /> <!-- don't change here -->


3. Inject Dao object to your class

   @Resource(name="dao")
   Dao dao;

4. Enjoy (reference: MainModule.java)

   dao.fetch();
   dao.insert();
   dao.update();
   dao.query();
   ....