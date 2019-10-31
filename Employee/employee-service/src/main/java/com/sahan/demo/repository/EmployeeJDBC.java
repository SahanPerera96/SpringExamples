package com.sahan.demo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahan.demo.modal.Employee;

import com.sahan.demo.modal.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Repository
public class EmployeeJDBC {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Autowired
//    JdbcTemplate jdbcTemplate1;


    public void setDataSource(QueryGenerator query){
        String connName = query.getRoot();
        String connPassword = query.getPassword();
        String myDriver = query.getMyDriver();
        String myUrl =  query.getMyUrl();

        DataSource dataSource = new DataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return null;
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            @Override
            public PrintWriter getLogWriter() throws SQLException {
                return null;
            }

            @Override
            public void setLogWriter(PrintWriter out) throws SQLException {

            }

            @Override
            public void setLoginTimeout(int seconds) throws SQLException {

            }

            @Override
            public int getLoginTimeout() throws SQLException {
                return 0;
            }

            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                return null;
            }
        };
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(myDriver);
        dataSourceBuilder.url(myUrl);
        dataSourceBuilder.username(connName);
        dataSourceBuilder.password(connPassword);
        dataSource = dataSourceBuilder.build();
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }


    public Employee findById(int id) {
        return jdbcTemplate.queryForObject("select * from employee where id=?", new Object[] {
                        id
                },
                new BeanPropertyRowMapper< Employee >(Employee.class));
    }

    public List< Object > findAll(QueryGenerator query) {
        String exeQuery = query.getQuery();

        setDataSource(query);
        return jdbcTemplate.query(exeQuery, new EmployeeRowMapper());
    }


    public class EmployeeRowMapper implements RowMapper< Object > {

        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            ResultSetMetaData rsMeta = rs.getMetaData();
            int colCount = rsMeta.getColumnCount();

            Map<String, Object> columns = new HashMap<>();
            for (int i = 1; i <= colCount; i++) {
                columns.put(rsMeta.getColumnLabel(i), rs.getObject(i));
            }
//        String json = "{\"prop1\": \"value1\" , \"prop2\": 123}";
//            ObjectMapper mapper = new ObjectMapper();
//        Map<String,Object> result;
//
//        {
//            try {
//                result = mapper.readValue(json,new TypeReference<Map<String,Object>>() {});
//                result.get("prop1"); // "value1"
//                result.get("prop2"); // 123
//                System.out.println(result);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

            return columns;
        }

    }
//Hibernate: drop table if exists address
//Hibernate: drop table if exists employee
//Hibernate: drop table if exists employee_project
//Hibernate: drop table if exists project
//Hibernate: drop table if exists telephone
//Hibernate: create table address (id integer not null auto_increment, city varchar(255), town varchar(255), primary key (id)) engine=MyISAM
//Hibernate: create table employee (id integer not null auto_increment, name varchar(255), address_id integer, primary key (id)) engine=MyISAM
//Hibernate: create table employee_project (eid integer not null, pid integer not null) engine=MyISAM
//Hibernate: create table project (id integer not null auto_increment, name varchar(255), primary key (id)) engine=MyISAM
//Hibernate: create table telephone (id integer not null auto_increment, number varchar(255), employee_id integer, primary key (id)) engine=MyISAM
//Hibernate: alter table employee add constraint FKga73hdtpb67twlr9c1i337tyt foreign key (address_id) references address (id)
//Hibernate: alter table employee_project add constraint FKl1rgfgpoe6sdo8kauiv3yy6r2 foreign key (pid) references project (id)
//Hibernate: alter table employee_project add constraint FKd7sgjwmuoofocloupa5k6hvep foreign key (eid) references employee (id)
//Hibernate: alter table telephone add constraint FKcxxlkas67owc6xwb6neqpjf62 foreign key (employee_id) references employee (id)
}
