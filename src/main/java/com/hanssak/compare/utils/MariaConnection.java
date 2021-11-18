package com.hanssak.compare.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaConnection implements DbConnection{
  @Override
  public Connection connection(String ip, String port, String dbName, String userId, String passwd) {
    Connection conn = null;

    final String driver = "com.mysql.jdbc.Driver";
    final String url = "dbc:mysql://"+ip+":"+port+"/"+dbName+"?connectTimeout=30000&socketTimeout=30000";
    final String id = userId;
    final String password = passwd;

    try{
      Class.forName(driver);
      conn = DriverManager.getConnection(url, id, password);
    }catch(SQLException | ClassNotFoundException e){
      e.printStackTrace();
    }

    return conn;
  }
}
