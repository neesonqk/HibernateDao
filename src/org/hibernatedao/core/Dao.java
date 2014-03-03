package org.hibernatedao.core;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernatedao.assist.CndTool;
import org.hibernatedao.assist.Condition;
import org.hibernatedao.assist.Messenger;
import org.hibernatedao.assist.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.test.pojo.Pet;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nesson on 12/19/13.
 */
@Transactional
@Component("dao")
public class Dao {

    @Resource(name = "sessionFactory")
    public SessionFactory sessionFactory;

    protected Dao dao;

    public Dao() {
        dao = this;
    }

    public <T> void insert(final T o) {
        sessionFactory.getCurrentSession().save(o);
    }

    public <T> void delete(final T o) {
        sessionFactory.getCurrentSession().delete(o);
    }

    public <T> void delete(Class<T> c, Integer id) {

        T toBeDeletedObj = this.fetch(c, id);

        sessionFactory.getCurrentSession().delete(toBeDeletedObj);
    }


    public <T> void update(final T o) {
        sessionFactory.getCurrentSession().update(o);
    }

    public <T> T fetch(Class<T> c, long id) {
        return (T) this.sessionFactory.getCurrentSession().get(c, id);
    }

    public <T> T fetch(Class<T> c, int id) {
        return (T) this.sessionFactory.getCurrentSession().get(c, id);
    }

    public <T> T fetch(Class<T> c, String name) {

        Field[] fields = c.getDeclaredFields();

        List<String> rule = Arrays.asList("org.hibernatedao.annotation.Name", "javax.persistence.Column");

        for (Field f : fields) {

            Annotation[] annotations = f.getAnnotations();
            List<String> anno_str = new ArrayList<String>();

            for (Annotation a : annotations) {
                anno_str.add(a.annotationType().getName());
            }

            if (anno_str.containsAll(rule)) {
                String sql = String.format("from %s where %s = ?", c.getName(), f.getName());
                Query query = sessionFactory.getCurrentSession().createQuery(sql);
                query.setString(0, name);
                return (T) query.uniqueResult();
            }
        }

        return null;
    }

    /**
     * calculate total number's record of table
     */
    public <T> Number count(Class<T> c) {
        return count(c, null);
    }

    /**
     * calculate total number's record of table base on condition(s)
     */
    public <T> Number count(Class<T> c, Condition cnd) {

        Criteria cri = sessionFactory.getCurrentSession().createCriteria(c);

        if (cnd != null)
            for (Messenger msg : cnd.getMessages()) {
                if (msg.criterion != null) cri.add(msg.criterion);
                if (msg.order != null) cri.addOrder(msg.order);
            }

        return (Number) cri.setProjection(Projections.rowCount()).uniqueResult();
    }

    public <T> List<T> query(Class<T> c, Page page) {
        return this.query(c, null, page);
    }

    public <T> List<T> query(Class<T> c) {
        return this.query(c, null, null);
    }

    public <T> List<T> query(Class<T> c, Condition cnd) {
        return this.query(c, cnd, null);
    }

    public <T> List<T> query(Class<T> c, Condition cnd, Page page) {

        Criteria cri = sessionFactory.getCurrentSession().createCriteria(c);

        if (cnd != null)
            for (Messenger msg : cnd.getMessages()) {
                if (msg.criterion != null) cri.add(msg.criterion);
                if (msg.order != null) cri.addOrder(msg.order);
            }

        if (page != null) {
            int limitFrom = page.getPageSize() * (page.getPageNumber() - 1);
            int limitTo = page.getPageSize() * page.getPageNumber();

            cri.setFirstResult(limitFrom);
            cri.setMaxResults(limitTo - limitFrom);
        }

        return cri.list();
    }

    /**
     * delete table content
     */
    public <T> void clear(Class<T> c) {
        String sql = String.format("delete %s", c.getName());
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.executeUpdate();
    }

    public <T> T fetch(Class<T> c, Condition cnd) {

        Criteria cri = sessionFactory.getCurrentSession().createCriteria(c);

        for (Messenger msg : cnd.getMessages()) {
            if (msg.criterion != null) cri.add(msg.criterion);
            if (msg.order != null) cri.addOrder(msg.order);
        }

        return (T) cri.uniqueResult();
    }
}
