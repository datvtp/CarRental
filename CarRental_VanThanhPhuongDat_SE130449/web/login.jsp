<%-- 
    Document   : login
    Created on : Mar 5, 2020, 8:48:39 PM
    Author     : vanth
--%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Car Rental</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>

    </head>
    <body>
        <h1>Login Page</h1>
        <a href="home.jsp">Home</a><br/><br/>
    <center>
        <h2>Login to continue</h2>
        <div style="padding: 20px; margin: 50px; text-align: left; width: 300px; min-height: 50px; background-color: #F5F5F5; border: 1px solid #CDCDCD;">
            <s:form action="LoginAction" method="POST">
                <s:textfield name="email" label="Email"/>
                <s:password name="password" label="Password"/>
                <s:submit value="Login" />
                <font color="red">
                <s:property value="#request.INVALID"/>
                </font>
                <div class="g-recaptcha" data-sitekey="6Ldsfd8UAAAAAHDzDhKxJohJWwHxhgxV0Rauc1k2"></div>
            </s:form>
        </div>
    </center>
</body>
</html>
