<%-- 
    Document   : confirmEmail
    Created on : Mar 6, 2020, 9:16:29 PM
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
    <center>
        <h2>Confirm Email to continue</h2>
        <div style="padding: 20px; margin: 50px; text-align: left; width: 300px; min-height: 50px; background-color: #F5F5F5; border: 1px solid #CDCDCD;">
            <s:form action="ConfirmEmailAction" method="POST">
                <s:textfield name="code" label="Enter Code"/>
                <s:submit value="Confirm" />
            </s:form>
        </div>
    </center>
</body>
</html>
