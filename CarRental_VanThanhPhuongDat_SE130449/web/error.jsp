<%-- 
    Document   : error
    Created on : Mar 5, 2020, 9:46:29 PM
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
        <h1>ERROR PAGE</h1>
        <font color="red">
        <s:property value="#request.ERROR"/>
        </font>
    </body>
</html>
