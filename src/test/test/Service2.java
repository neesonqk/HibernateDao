package test.test;

import org.hibernate.SessionFactory;
import org.hibernatedao.core.Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.test.pojo.Master;

import javax.annotation.Resource;

/**
 * Created by Nesson on 3/29/2014.
 */
@Service(value = "xxx")
@Transactional
public class Service2 {


    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    public Master getMaster(){

        return (Master) sessionFactory.getCurrentSession().createQuery("from Master where master_id = 1").uniqueResult();
    }
}
