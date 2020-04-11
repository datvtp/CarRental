<%-- 
    Document   : home
    Created on : Mar 5, 2020, 8:47:21 PM
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
        <h1>Home Page</h1>
        <a href="login.jsp">Login</a> <br/>
        <a href="register.jsp">Register</a> <br/><br/>

        <s:if test="%{numberOfPage > 1}">
            <s:iterator begin="1" end="%{numberOfPage}" var="i">
                <div style="float: left; padding-left: 20px;s">
                    <s:form action="GuestSearchAction" method="POST">
                        <s:hidden name="name" value="%{name}"/>
                        <s:hidden name="category" value="%{category}"/>
                        <s:hidden name="rentalDate" value="%{rentalDate}"/>
                        <s:hidden name="returnDate" value="%{returnDate}"/>
                        <s:hidden name="amount" value="%{amount}"/>
                        <s:hidden name="pageNumber" value="%{i}"/>
                        <s:submit value="%{#i}"/>
                    </s:form>
                </div>
            </s:iterator>
        </s:if>
        <br/><br/><br/><br/><br/><br/>


    <center>
        <s:form action="GuestSearchAction" method="POST">
            <s:textfield name="name" label="Car Name" value="%{name}"/>
            <s:select label="Category" name="category" list="%{#session.CATEGORY}" headerKey="0" headerValue="All" listKey="%{categoryID}" listValue="%{categoryName}"/>
            <s:textfield name="rentalDate" type="date" value="%{rentalDate}" label="Rental"/>
            <s:textfield name="returnDate" type="date" value="%{returnDate}" label="Return"/>
            <s:if test="%{amount > 0}">
                <s:textfield name="amount" type="number" label="Amount" min="1" value="%{amount}"/>
            </s:if>
            <s:else>
                <s:textfield name="amount" type="number" label="Amount" min="1" value=""/>
            </s:else>
            <s:hidden name="pageNumber" value="1"/>
            <s:submit value="Search"/>
        </s:form>
    </center>


    <center>
        <s:if test="%{list.isEmpty()}">
            <h3>No record found.</h3>
        </s:if>
        <s:else>
            <s:iterator value="%{list}">
                <div style="float: left;padding: 20px; margin: 30px; text-align: left; width: 200px; min-height: 200px; background-color: #F5F5F5; border: 1px solid #CDCDCD;">
                    <img src="images/<s:property value="%{image}"/>" alt="" width="200" height="200"/>
                    <h6>Name: <s:property value="%{name}"/></h6>
                    <h6>Color: <s:property value="%{color}"/></h6>
                    <h6>Year: <s:property value="%{year}"/></h6>
                    <h6>Price: <s:property value="%{price}"/>$</h6>
                    <h6>Quantity: <s:property value="%{quantity}"/></h6>
                    <h6>Category: <s:property value="%{categoryName}"/></h6>
                    <s:form action="UserAddToCartAction" method="POST">
                        <s:hidden name="carID" value="%{carID}"/>
                        <s:hidden name="carName" value="%{name}"/>
                        <s:hidden name="categoryName" value="%{categoryName}"/>
                        <s:hidden name="rentalDate" value="%{rentalDate}"/>
                        <s:hidden name="returnDate" value="%{returnDate}"/>
                        <s:hidden name="price" value="%{price}"/>
                        <s:submit value="Rent"/>
                    </s:form>
                </div>
            </s:iterator>
        </s:else>
    </center>
</body>
</html>
