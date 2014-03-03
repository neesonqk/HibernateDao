package org.hibernatedao.assist;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Set;

/**
 * Created by Nesson on 2/26/14.
 */
public interface Condition {

    public Set<Messenger> getMessages();
    public void clearCache();
}
