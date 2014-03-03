package org.hibernatedao.assist;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class Messenger {
    public Messenger(Criterion c, Order o){
        criterion = c;
        order = o;
    }
    public Messenger(Criterion c){
        criterion = c;
    }
    public Messenger(Order o){
        order = o;
    }
    public Messenger(){}
	public Order order = null;
	public Criterion criterion = null;
}
