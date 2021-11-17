package compare.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class MariaConnection implements DbConnection{
  @Override
  public Connection connection() {
    Connection conn = null;

    final String driver = "com.mysql.jdbc.Driver";
    final String url = "dbc:mysql://192.168.17.199/isbsdb?connectTimeout=30000&socketTimeout=30000";
    final String id = "isbs";
    final String password = "isbs2301";

    try{
      Class.forName(driver);

      conn = DriverManager.getConnection(url, id, password);

      log.info("Maria DB Connect Success!!");
    }catch(SQLException | ClassNotFoundException e){
      e.printStackTrace();
    }

    return conn;
  }
}
