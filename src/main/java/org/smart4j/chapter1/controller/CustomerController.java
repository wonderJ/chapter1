package org.smart4j.chapter1.controller;

import org.smart4j.chapter1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by 陈亮 on 2016/11/25.
 */
@Controller("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

//    @Action("delete:/customer_edit")
//    public void delete(){
//        customerService.deleteCustomer(Param param){
//
//        }
//    }

}
