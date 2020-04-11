<%-- 
    Document   : userViewCart
    Created on : Mar 12, 2020, 9:07:54 PM
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
        <h1>Shopping Cart</h1>
        <div style="padding: 20px; margin: 30px; text-align: left; width: 700px; min-height: 200px; background-color: #F5F5F5; border: 1px solid #CDCDCD;">
            <s:if test="%{#session.CART.shoppingCart.isEmpty()}">
                <h3>Empty Cart.</h3>
            </s:if>
            <s:else>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Car Name</th>
                            <th>Category</th>
                            <th>Rental</th>
                            <th>Return</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="%{#session.CART.shoppingCart.values()}">
                            <tr>
                                <td><s:property value="%{carName}"/></td>
                                <td><s:property value="%{categoryName}"/></td>
                                <td><s:property value="%{rentalDate}"/></td>
                                <td><s:property value="%{returnDate}"/></td>
                                <td><s:property value="%{price}"/>$</td>
                                <td>
                                    <s:form action="UserUpdateCartAction" method="POST">
                                        <s:textfield type="number" name="quantity" value="%{quantity}" min="1"/>
                                        <s:hidden name="id" value="%{id}"/>
                                        <s:submit value="Update"/>
                                    </s:form>
                                </td>
                                <td><s:property value="%{calTotalPrice()}"/>$</td>
                                <td>
                                    <s:form action="UserDeleteCartAction" method="POST">
                                        <s:hidden name="id" value="%{id}"/>
                                        <s:submit value="Delete" onclick="return confirm('Are you sure you want to delete?')"/>
                                    </s:form>
                                </td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
                <h3>Total amount of money of this cart: <s:property value="%{#session.CART.getTotal()}"/> $</h3> <br/><br/>
                <s:form action="UserPayAction" method="POST">
                    <s:textfield name="discount" value="" label="Discount Code"/>
                    <font color="red">
                    <s:property value="#request.INVALID_DISCOUNT"/> <br/>
                    </font>
                    <s:if test="%{#request.LIST_CAR_OUT_STOCK.isEmpty() == false}">
                        <font color="red">
                        <s:iterator value="%{#request.LIST_CAR_OUT_STOCK}">
                            <s:property value="%{carName}"/> is out of stock <br/>
                        </s:iterator>
                        </font>
                    </s:if>
                    <s:submit value="Pay"/>
                </s:form>
            </s:else>
        </div>
    </center>
</body>
</html>
