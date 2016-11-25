package org.smart4j.chapter1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.helper.DatabaseHelper;
import org.smart4j.chapter1.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by 陈亮 on 2016/11/22.
 */
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    public List<Customer> getCustomerList() {
            String sql = "select * from customer";
            return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    public Customer getCustomer(int id) {
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    public boolean updateCustomer(int id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    public boolean deleteCustomer(int id) {
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }
}
