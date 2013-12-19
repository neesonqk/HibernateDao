package org.hibernatedao.assist;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Condition {
	
	protected String order_colo = null;
	
	public String restriction_colo = null;
	
	private String restriction_cnd = null;
	
	public Object restriction_value = null;
	
	public Condition(){}
	
	public Condition(String colo, String cnd, Object value){
		this.restriction_cnd = cnd;
		this.restriction_colo = colo;
		this.restriction_value = value;
	}

	public Condition desc(String colo){
		this.order_colo = colo;
		return this;
	}
	
	public Order getOrder(){
		return this.order_colo == null ? null : Order.desc(this.order_colo);
	}
	
	public CndReturnType getRestrications(){
	
		CndReturnType crt = new CndReturnType();
		
		//修复一个bug，如果不判断null值， 那么当没有限制条件传入的时候，因为restriction_cnd默认是null，就会抛出null异常
		if(this.restriction_cnd == null) return crt;
		
		switch(this.restriction_cnd){
		case ">=" :
			crt.simpleExpression = Restrictions.ge(this.restriction_colo, this.restriction_value);
			break;
		case ">" :
			crt.simpleExpression = Restrictions.gt(this.restriction_colo, this.restriction_value);
			break;
		case "=" :
			crt.simpleExpression = Restrictions.eq(this.restriction_colo, this.restriction_value);
			break;
		case "<" :
			crt.simpleExpression = Restrictions.lt(this.restriction_colo, this.restriction_value);
			break;
		case "<=" :
			crt.simpleExpression = Restrictions.le(this.restriction_colo, this.restriction_value);
			break;
		case "!=" :
			crt.criterion = Restrictions.not(Restrictions.eq(this.restriction_colo, this.restriction_value));
			break;
		default:
			return crt;
		}
		return crt;
	}
}
