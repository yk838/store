package com.cy.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired//自动装配
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }

    /**
     * 数据库
     *  数据库连接池：
     *  1.jdbc
     *  2.c3p0
     *  3.Hikari(底层c3p0数据库管理对象)
     * HikariProxyConnection@1812530678 wrapping com.mysql.cj.jdbc.ConnectionImpl@23a918c7
     * @throws SQLException
     */
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }


}
