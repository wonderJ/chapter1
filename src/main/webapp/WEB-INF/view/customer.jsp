<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 陈亮
  Date: 2016/11/22
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>客户列表</h1>
<table>
    <tr>
        <th>客户名称</th>
        <th>联系人</th>
        <th>电话</th>
        <th>邮箱地址</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${customerList}" var="customer">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telephone}</td>
            <td>${customer.email}</td>
            <td>
                <a href="${Base}/customer_edit?id=${customer.id}">修改</a>
                <a href="${Base}/customer_delete?id=${customer.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
