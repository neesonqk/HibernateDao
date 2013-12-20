package org.hibernatedao.core;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernatedao.assist.CndReturnType;
import org.hibernatedao.assist.Condition;
import org.hibernatedao.assist.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource(name="sessionFactory")
    public SessionFactory sessionFactory;

    public Logger log = Logger.getLogger(this.getClass());


    /**
    * calculate total number's record of table
    */
    public Number count(Class<?> c){
        return (Number)sessionFactory.getCurrentSession().createCriteria(c).setProjection(Projections.rowCount()).uniqueResult();
    }

    /**
    * calculate total number's record of table base on condition(s)
    * */
    public Number count(Class<?> c, Condition... cnds){
        Criteria cri = sessionFactory.getCurrentSession().createCriteria(c);
        for(Condition cnd : cnds){
            CndReturnType crt = cnd.getRestrications();
            if(crt.criterion != null) cri.add(crt.criterion);
            if(crt.simpleExpression != null) cri.add(crt.simpleExpression);
        }
        return (Number) cri.setProjection(Projections.rowCount()).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public <T> T fetch(Class<T> c, int id){
        return (T) this.sessionFactory.getCurrentSession().get(c, id);
    }

    //@Name  @Column(unique = true)
    @SuppressWarnings("unchecked")
    public <T> T fetch(Class<T> c, String name) {
        try{
            Field[] fields = c.getDeclaredFields();
            List<String> rule = Arrays.asList("org.hibernatedao.annotation.Name", "javax.persistence.Column");
            for(Field f : fields){
                Annotation[] annotations = f.getAnnotations();
                List<String> anno_str = new ArrayList<String>();
                for(Annotation a : annotations){
                    anno_str.add(a.annotationType().getName());
                }

                if(anno_str.containsAll(rule)){
                    String sql = String.format("from %s where %s = ?", c.getName(), f.getName());
                    Query query = sessionFactory.getCurrentSession().createQuery(sql);
                    query.setString(0, name);
                    return (T) query.uniqueResult();
                }
            }
        }catch(Exception ex){
            log.error(ex.toString());
            return null;
        }
        return null;
    }

    public <T> boolean delete(Class<T> c, int id){
        try{
            T toBeDeletedObj = this.fetch(c, id);
            sessionFactory.getCurrentSession().delete(toBeDeletedObj);
            return true;
        }catch(HibernateException ex){
            log.error(ex.toString());
            return false;
        }
    }

    public boolean delete(Object o){
        try{
            sessionFactory.getCurrentSession().delete(o);
            return true;
        }catch(HibernateException ex){
            log.error(ex.toString());
            return false;
        }
    }

    public boolean update(Object o){
        try{
            sessionFactory.getCurrentSession().update(o);
            return true;
        }catch(HibernateException ex){
            return false;
        }
    }

    public boolean insert(Object o){
        try{
            sessionFactory.getCurrentSession().save(o);
            return true;
        }catch(HibernateException ex){
            System.out.println(ex.toString());
            return false;
        }
    }

    public <T> List<T> query(Class<T> c, Page page){
        return this.query(c, page, new Condition[0]);
    }

    public <T> List<T> query(Class<T> c){
        return this.query(c, null, new Condition[0]);
    }

    public <T> List<T> query(Class<T> c, Condition... cnd){
        return this.query(c, null, cnd);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> query(Class<T> c, Page page, Condition... cnds){
        try {

            Criteria cri = sessionFactory.getCurrentSession().createCriteria(c);
            if (cnds.length != 0) {
                for (Condition cnd : cnds) {
                    if (cnd != null) {
                        if (cnd.getOrder() != null)
                            cri.addOrder(cnd.getOrder());
                        CndReturnType ctr = cnd.getRestrications();
                        if (ctr.criterion != null)
                            cri.add(ctr.criterion);
                        if (ctr.simpleExpression != null)
                            cri.add(ctr.simpleExpression);
                    }
                }
            }
            if(page != null){
                int limitFrom = page.getPageSize() * (page.getPageNumber() - 1);
                int limitTo = page.getPageSize() * page.getPageNumber();

                cri.setFirstResult(limitFrom);
                cri.setMaxResults(limitTo - limitFrom);
            }

            return cri.list();

        }catch(HibernateException hex){
            log.error(hex.toString());
            System.out.println(hex.toString());
            return null;
        }
    }

    /**
     * delete table content
     */
    public <T> boolean clear(Class<T> c){
        try{
            String sql = String.format("delete %s", c.getName());
            Query query = sessionFactory.getCurrentSession().createQuery(sql);
            query.executeUpdate();
            return true;
        }catch (HibernateException hex){
            return false;
        }
    }

    //Condition ‘=’
    @SuppressWarnings("unchecked")
    public <T> T fetch(Class<T> c, Condition... cnds){
        try{
            Criteria cri = sessionFactory.getCurrentSession().createCriteria(c);
            for(Condition cnd :cnds)
                cri.add(Restrictions.eq(cnd.restriction_colo, cnd.restriction_value));
            return (T) cri.uniqueResult();
        }catch(HibernateException hex){
            log.error(hex.toString());
            return null;
        }
    }
}
