package org.hibernatedao.assist;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Condition {
	
	protected String order_colo = null;
	
	public String restriction_colo = null;
	
	private String restriction_cnd = null;
	
	public Object restriction_value = null;

    private boolean asc = true;
	
	public Condition(){}
	
	public Condition(String colo, String cnd, Object value){
		this.restriction_cnd = cnd;
		this.restriction_colo = colo;
		this.restriction_value = value;
	}

    public Condition desc(String colo){
        this.order_colo = colo;
        asc = false;
        return this;
    }

    public Condition asc(String colo){
        this.order_colo = colo;
        asc = true;
        return this;
    }

    public Order getOrder(){
        return this.order_colo == null ? null :
                asc ? Order.asc(this.order_colo) : Order.desc(this.order_colo);
    }

    public CndReturnType getRestrications(){
	
		CndReturnType crt = new CndReturnType();

		if(this.restriction_cnd == null) return crt;
		
		switch(this.restriction_cnd){
		case ">=" :
			crt.simpleExpression = Restrictions.ge(this.restriction_colo, this.restriction_value);
			break;
		case ">" :
			crt.simpleExpression = Restrictions.gt(this.restriction_colo, this.restriction_value);
			break;
		case "=" :
            if(this.restriction_value == null) {
                crt.criterion = Restrictions.isNull(this.restriction_colo);
                break;
            }
			crt.simpleExpression = Restrictions.eq(this.restriction_colo, this.restriction_value);
			break;
		case "<" :
			crt.simpleExpression = Restrictions.lt(this.restriction_colo, this.restriction_value);
			break;
		case "<=" :
			crt.simpleExpression = Restrictions.le(this.restriction_colo, this.restriction_value);
			break;
		case "!=" :
            if(this.restriction_value == null) {
                crt.criterion = Restrictions.isNotNull(this.restriction_colo);
                break;
            }
			crt.criterion = Restrictions.not(Restrictions.eq(this.restriction_colo, this.restriction_value));
			break;
		default:
			return crt;
		}
		return crt;
	}
}
