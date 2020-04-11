<%-- 
    Document   : userViewInvoice
    Created on : Mar 13, 2020, 10:03:49 PM
    Author     : vanth
--%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Rental</title>
    </head>
    <body>
        <h1>Welcome, <s:property value="#session.NAME"/></h1>
        <a href="user.jsp">Home</a> <br/><br/>

    <center>
        <h1>View Invoice</h1>
        <div style="padding: 20px; margin: 30px; text-align: left; width: 300px; min-height: 200px; background-color: #F5F5F5; border: 1px solid #CDCDCD;">
            Invoice ID: <s:property value="#request.INVOICE_DTO.invoiceID"/> <br/><br/>
            Create Time: <s:property value="#request.INVOICE_DTO.createTime"/> <br/><br/>
            Total Price: <s:property value="#request.INVOICE_DTO.totalPrice"/>$ <br/><br/>
            Email: <s:property value="#request.INVOICE_DTO.email"/> <br/><br/>
            <s:if test="%{#request.INVOICE_DTO.discountID > 0}">
                Discount ID: <s:property value="#request.INVOICE_DTO.discountID"/> <br/><br/>
            </s:if>
        </div>
    </center>
</body>
</html>
