package org.hibernatedao.assist;

import org.hibernate.criterion.LikeExpression;

/**
 * Created by Nesson on 2/27/14.
 */
public class EscapeLikeExpress extends LikeExpression {

    public EscapeLikeExpress(String propertyName, String value, Character escapeChar, boolean ignoreCase) {
        super(propertyName, value, escapeChar, ignoreCase);
    }

}
