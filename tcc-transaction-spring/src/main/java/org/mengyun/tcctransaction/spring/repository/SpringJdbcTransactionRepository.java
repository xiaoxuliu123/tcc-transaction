package org.mengyun.tcctransaction.spring.repository;


import org.mengyun.tcctransaction.repository.JdbcTransactionRepository;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by changmingxie on 10/30/15.
 */

@Component
public class SpringJdbcTransactionRepository extends JdbcTransactionRepository {

    protected Connection getConnection() {
        return DataSourceUtils.getConnection(this.getDataSource());
    }

    protected void releaseConnection(Connection con) {
        DataSourceUtils.releaseConnection(con, this.getDataSource());
    }
}
