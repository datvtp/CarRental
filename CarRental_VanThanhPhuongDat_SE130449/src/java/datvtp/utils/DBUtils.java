package datvtp.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtils implements Serializable {

    public static Connection getConnection() throws NamingException, SQLException {
        Context ctx = new InitialContext();
        Context tomcat = (Context) ctx.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcat.lookup("DBCon");
        return ds.getConnection();
    }
}
