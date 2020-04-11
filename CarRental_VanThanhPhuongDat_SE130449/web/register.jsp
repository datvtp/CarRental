<%-- 
    Document   : register
    Created on : Mar 5, 2020, 8:49:15 PM
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
        <h1>Register Page</h1>
        <a href="home.jsp">Home</a><br/><br/>
    <center>
        <h2>Register to continue</h2>
        <div style="padding: 20px; margin: 50px; text-align: left; width: 300px; min-height: 50px; background-color: #F5F5F5; border: 1px solid #CDCDCD;">
            <s:form action="RegisterAction" method="POST">
                <s:textfield name="email" label="Email"/>
                <s:password name="password" label="Password"/>
                <s:password name="confirm" label="Confirm Password"/>
                <s:textfield name="name" label="Name"/>
                <s:textfield name="phone" label="Phone"/>
                <s:textfield name="address" label="Address"/>
                <s:submit value="Register" />
            </s:form>
        </div>
    </center>
</body>
</html>
