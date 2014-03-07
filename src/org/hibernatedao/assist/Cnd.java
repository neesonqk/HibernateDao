package org.hibernatedao.assist;

import org.hibernate.criterion.*;

import java.util.*;

/**
 * Created by Nesson on 2/28/14.
 */
public class Cnd implements Condition{

    public Set<Messenger> messengers = new HashSet<>();

    public Set<Messenger> getMessages(){
        return messengers;
    }

    public void setMessengers(Set<Messenger> messengers) {
        this.messengers = messengers;
    }

    public void clearCache(){
        messengers.clear();
    }

    public Cnd(){}

    public Cnd(Set<Messenger> msgs){
        messengers = msgs;
    }

    public static Cnd where(){
        return new Cnd();
    }

    public static Cnd where(String column, String condition, Object value){
        return new Cnd().setMessengers(column, condition, value);
    }

    public static Cnd where(String column, String condition, Object[] values){
        return new Cnd().setMessengers(column, condition, values);
    }

    public static Cnd where(Condition condition){

        Cnd cnd_ = new Cnd();
        cnd_.setMessengers(condition.getMessages());
        return cnd_;
    }

    public static Cnd where(String column, String condition, String value, Character escapeChar, boolean ignoreCase){
        return (condition.toUpperCase().equals("LIKE")) ? new Cnd().setMessengers(column, condition, value,escapeChar, ignoreCase)
                                                        : new Cnd();
    }

    public static Cnd where(String column, String condition, String value, Character escapeChar){
        return where(column, condition, value, escapeChar, true);
    }

    public Cnd and(String column, String condition, Object value){
        messengers.add(wrapMessenger(column, condition, value));
        return this;
    }

    public Cnd andIn(String column, Object[] values){
        messengers.add(wrapMessenger(column, "IN", values));
        return this;
    }

    public Cnd andNotIn(String column, Objects[] values){
        messengers.add(wrapMessenger(column, "NOT IN", values));
        return this;
    }

    public Cnd and(String column, String condition, String value, Character escapeChar){
        return and(column, condition, value, escapeChar, true);
    }

    public Cnd and(String column, String condition, String value, Character escapeChar, boolean ignoreCase){
        if(condition.toUpperCase().equals("LIKE"))
            messengers.add(wrapMessengerForLike(column, value, escapeChar, ignoreCase));
        return this;
    }

    public Cnd and(Condition condition){
        for(Messenger messenger : condition.getMessages()){
            messengers.add(messenger);
        }
        return this;
    }

    public Cnd or(String column, String condition, Object value){

        Criterion criterion = wrapCriterion(column, condition, value);

        repackMessengersForOr(criterion);

        return this;
    }

    public Cnd or(Condition condition){

        Conjunction conjunction = Restrictions.conjunction();

        List<Order> orders = new ArrayList<>();

        for(Messenger messenger : messengers){
            if(messenger.criterion != null){
                conjunction.add(messenger.criterion);
            }else{
                orders.add(messenger.order);
            }
        }

        Conjunction conjunction_or = Restrictions.conjunction();

        for(Messenger messenger : condition.getMessages()){
            if(messenger.criterion != null){
                conjunction_or.add(messenger.criterion);
            }else{
                orders.add(messenger.order);
            }
        }

        messengers.clear();

        messengers.add(new Messenger(Restrictions.disjunction().add(conjunction).add(conjunction_or)));

        for(Order o : orders){
            messengers.add(new Messenger(o));
        }

        return this;
    }

    public Cnd orIn(String column, Object[] values){

        Criterion criterion = wrapCriterion(column, "IN", values);

        repackMessengersForOr(criterion);

        return this;
    }

    public Cnd orNotIn(String column, Objects[] values){

        Criterion criterion = wrapCriterion(column, "NOT IN", values);

        repackMessengersForOr(criterion);

        return this;
    }

    private void repackMessengersForOr(Criterion c){

        Conjunction conjunction = Restrictions.conjunction();

        List<Order> orders = new ArrayList<>();

        //fix bug, or condition conflicts with desc and asc and
        //makes order information lost
        for(Messenger messenger : messengers){
            if(messenger.criterion != null){
                conjunction.add(messenger.criterion);
            }else{
                orders.add(messenger.order);
            }
        }

        messengers.clear();

        messengers.add(new Messenger(Restrictions.disjunction().add(c).add(conjunction)));

        //bug fixed, order information lost
        for(Order o : orders){
            messengers.add(new Messenger(o));
        }
    }

    public Cnd or(String column, String condition, String value, Character escapeChar, boolean ignoreCase){

        if(condition.toUpperCase().equals("LIKE")){

            Criterion criterion = wrapCriterion(column, condition, value);

            Conjunction conjunction = Restrictions.conjunction();

            for(Messenger messenger : messengers){
                conjunction.add(messenger.criterion);
            }

            messengers.clear();

            messengers.add(new Messenger(Restrictions.disjunction().add(criterion).add(conjunction)));
        }

        return this;
    }

    public Cnd or(String column, String condition, String value, Character escapeChar){
        return or(column, condition, value, escapeChar, true);
    }

    public Cnd asc(String column){
        messengers.add(new Messenger(Order.asc(column)));
        return this;
    }

    public Cnd desc(String column){
        messengers.add(new Messenger(Order.desc(column)));
        return this;
    }

    private Cnd setMessengers(String column, String condition, Object value){
        messengers.add(wrapMessenger(column, condition, value));
        return this;
    }

    //for IN condition
    private Cnd setMessengers(String column, String condition, Object[] values){
        messengers.add(wrapMessenger(column, condition, values));
        return this;
    }

    private Cnd setMessengers(String column, String condition, String value, Character escapeChar, boolean ignoreCase){
        messengers.add(wrapMessengerForLike(column, value, escapeChar, ignoreCase));
        return this;
    }

    private Messenger wrapMessenger(String column, String condition, Object[] values){
        return new Messenger(this.wrapCriterion(column, condition, values));
    }

    private Messenger wrapMessenger(String column, String condition, Object value){
        return new Messenger(this.wrapCriterion(column, condition, value));
    }

    private Messenger wrapMessengerForLike(String column, String value, Character escapeChar, boolean ignoreCase){
        return new Messenger(new EscapeLikeExpress(column, value, escapeChar,ignoreCase));
    }

    private Criterion wrapCriterion(String column, String condition, Object[] values){

        if(values == null || values.length == 0){
            return Restrictions.sqlRestriction("(1=0)");
        }

        switch (condition.toUpperCase()){
            case "IN" :
                return Restrictions.in(column, values);

            case "NOT IN":
                return Restrictions.not(Restrictions.in(column, values));
        }

        return null;
    }

    private Criterion wrapCriterion(String column, String condition, Object value){

        switch (condition.toUpperCase()){

            case "=" :
                return value == null ? Restrictions.isNull(column)
                        : Restrictions.eq(column, value);

            case ">" :
                return Restrictions.gt(column, value);

            case ">=":
                return Restrictions.ge(column, value);

            case "<" :
                return Restrictions.lt(column, value);

            case "<=" :
                return Restrictions.le(column, value);

            case "!=" :
                return value == null ? Restrictions.isNotNull(column)
                        : Restrictions.not(Restrictions.eq(column, value));
            case "LIKE" :
                return Restrictions.like(column, value.toString());

        }

        return null;
    }

    protected void finalize(){
        messengers.clear();
    }

}
