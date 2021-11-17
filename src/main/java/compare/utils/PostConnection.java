package compare.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class PostConnection implements DbConnection{


  @Override
  public Connection connection() {
    Connection conn = null;

    final String driver = "org.postgresql.Driver";
    final String url = "jdbc:postgresql://127.0.0.1:5432/hsckdb";
    final String id = "hsck";
    final String password = "hsck2301";

    try{
      Class.forName(driver);

      conn = DriverManager.getConnection(url, id, password);

      log.info("PostSql DB Connect Success!!");
    }catch(SQLException | ClassNotFoundException e){
      e.printStackTrace();
    }

    return conn;
  }
}
