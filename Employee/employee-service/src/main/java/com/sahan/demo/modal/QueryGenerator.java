package com.sahan.demo.modal;

import org.springframework.stereotype.Component;

@Component
public class QueryGenerator {

    String root;
    String password;
    String query;
    String myDriver;
    String myUrl;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMyDriver() {
        return myDriver;
    }

    public void setMyDriver(String myDriver) {
        this.myDriver = myDriver;
    }

    public String getMyUrl() {
        return myUrl;
    }

    public void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }
}
