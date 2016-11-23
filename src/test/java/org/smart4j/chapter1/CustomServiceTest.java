package org.smart4j.chapter1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈亮 on 2016/11/22.
 */
public class CustomServiceTest {
    private final CustomerService customerService;

    public CustomServiceTest() {
        customerService=new CustomerService();
    }

    @Before
    public void init(){

    }

    @Test
    public void getCustomerListTest() throws Exception{
        List<Customer> customers=customerService.getCustomerList();
        Assert.assertEquals(2,customers.size());
    }

    @Test
    public void getCustomerTest() throws Exception{
        int id=1;
        Customer customer=customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() throws Exception{
        Map<String,Object> fieldMap=new HashMap<String, Object>();
        fieldMap.put("name","张强");
        fieldMap.put("contact","哈哈");
        fieldMap.put("telephone","11313122555");
        fieldMap.put("email","1565888@qq.com");
        fieldMap.put("remark","讲课时发生");
        boolean result=customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() throws Exception{
        int id=1;
        boolean result=customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}
