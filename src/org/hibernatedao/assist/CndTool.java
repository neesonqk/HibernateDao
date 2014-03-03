package org.hibernatedao.assist;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nesson on 2/26/14.
 */
public class CndTool implements Condition{

    private Set<Messenger> messengers = new HashSet<>();

    public Set<Messenger> getMessages(){
        return messengers;
    }

    @Override
    public void clearCache() {

    }

    public CndTool where(){
        return this;
    }

    public CndTool where(String column, String condition, Object value){
        messengers.add(wrapCriterion(column, condition, value));
        return this;
    }

    //for like only
    public CndTool where(String column, String condition, String value, Character escapeChar){
        return where(column, condition, value, escapeChar, true);
    }

    public CndTool where(String column, String condition, String value, Character escapeChar, boolean ignoreCase){
        if(condition.toUpperCase().equals("LIKE"))
            messengers.add(wrapCriterionForLike(column, value, escapeChar,ignoreCase));
        return this;
    }

    public CndTool and(String column, String condition, Object value){
        return where(column, condition, value);
    }

    public CndTool and(String column, String condition, String value, Character escapeChar){
        return where(column, condition, value, escapeChar);
    }

    public CndTool and(String column, String condition, String value, Character escapeChar, boolean ignoreCase){
        return where(column, condition, value, escapeChar, ignoreCase);
    }

    public CndTool or(String column, String condition, Object value){
        messengers.add(new Messenger(Restrictions.disjunction()));
        return where(column, condition, value);
    }

    public CndTool or(String column, String condition, String value, Character escapeChar){
        messengers.add(new Messenger(Restrictions.disjunction()));
        return where(column, condition, value, escapeChar);
    }

    public CndTool or(String column, String condition, String value, Character escapeChar, boolean ignoreCase){
        messengers.add(new Messenger(Restrictions.disjunction()));
        return where(column, condition, value, escapeChar, ignoreCase);
    }

    public CndTool asc(String column){
        messengers.add(new Messenger(Order.asc(column)));
        return this;
    }

    public CndTool desc(String column){
        messengers.add(new Messenger(Order.desc(column)));
        return this;
    }

    private Messenger wrapCriterion(String column, String condition, Object[] values){

        switch (condition.toUpperCase()){

            case "IN" :
                return new Messenger(Restrictions.in(column, values));

            case "NOT IN":
                return new Messenger(Restrictions.not(Restrictions.in(column, values)));
        }

        return null;
    }

    private Messenger wrapCriterion(String column, String condition, Object value){

        switch (condition.toUpperCase()){

            case "=" :
                return value == null ? new Messenger(Restrictions.isNull(column)) :
                                       new Messenger(Restrictions.eq(column, value));

            case ">" :
                return new Messenger(Restrictions.gt(column, value));

            case ">=":
                return new Messenger(Restrictions.ge(column, value));

            case "<" :
                return new Messenger(Restrictions.lt(column, value));

            case "<=" :
                return new Messenger(Restrictions.le(column, value));

            case "!=" :
                return value == null ? new Messenger(Restrictions.isNotNull(column)) :
                                       new Messenger(Restrictions.not(Restrictions.eq(column, value)));

            case "LIKE" :
                return new Messenger(Restrictions.like(column, value.toString()));

        }

        return null;
    }

    private Messenger wrapCriterionForLike(String column, String value, Character escapeChar, boolean ignoreCase){
        return new Messenger(new EscapeLikeExpress(column, value, escapeChar,ignoreCase));
    }

    private Messenger wrapCriterion(String column, String value, Character escapeChar){
        return wrapCriterionForLike(column, value, escapeChar, true);
    }

    protected void finalize(){

    }
}
