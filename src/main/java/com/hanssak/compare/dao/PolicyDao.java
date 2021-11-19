package com.hanssak.compare.dao;

import com.hanssak.compare.domain.PolicyDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PolicyDao {

  public List<PolicyDto> getPolicy(Connection conn, String query) {

    ResultSet rs = null;
    List<PolicyDto> dtos = new ArrayList<>();
    try(Statement stmt = conn.createStatement();){

//      String query = "select svc.dest_ip, svc.dest_port, svc.dest_port2, svc.svc_mod, detail.src_ip"
//              +" from tbl_net_svc_port svc"
//              +" left outer join tbl_net_svc_port_detail_his detail on svc.svc_mid = detail.svc_mid ";

      rs = stmt.executeQuery(query);
      while (rs.next()){
        dtos.add(PolicyDto.builder()
                        .destIp(rs.getString(1).trim())
                        .destPort(rs.getInt(2))
                        .destPort2(rs.getInt(3))
                        .svcMod(rs.getString(4).trim())
                        .srcIp(rs.getString(5).trim())
                        .portRange(rs.getInt(2) + "~" + rs.getInt(3))
                .build());
      }

    }catch(SQLException e){
      e.printStackTrace();
    }finally {
      try{
        if( conn != null && !conn.isClosed()){
          conn.close();
        }
        if( rs != null && !rs.isClosed()){
          rs.close();
        }
      }catch( SQLException e){
        e.printStackTrace();
      }
    }

    return dtos;
  }
}
