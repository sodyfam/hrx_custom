package com.hanssak.compare.utils;

import java.sql.Connection;

public interface DbConnection {

  Connection connection(String ip, String port, String dbName, String userId, String passwd);
}
