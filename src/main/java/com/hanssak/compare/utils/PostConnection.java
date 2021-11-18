package com.hanssak.compare.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostConnection implements DbConnection{


  @Override
  public Connection connection(String ip, String port, String dbName, String userId, String passwd) {
    Connection conn = null;

    final String driver = "org.postgresql.Driver";
    final String url = "jdbc:postgresql://"+ip+":"+port+"/"+dbName;
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
