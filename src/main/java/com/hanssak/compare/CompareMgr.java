package com.hanssak.compare;

import com.hanssak.compare.dao.PolicyDao;
import com.hanssak.compare.domain.PolicyDto;
import com.hanssak.compare.utils.DataConverter;
import com.hanssak.compare.utils.DbConnection;
import com.hanssak.compare.utils.PostConnection;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CompareMgr {

  public static void main(String[] args) {
    String ip;
    String port;
    String dbName;
    String userId;
    String userPwd;


    Scanner scan = new Scanner(System.in);
    System.out.print("이전 DB IP : ");    ip = scan.next();
    System.out.print("이전 DB PORT : ");  port = scan.next();
    System.out.print("이전 DB DB명 : ");  dbName = scan.next();
    System.out.print("이전 DB ID : ");    userId = scan.next();
    System.out.print("이전 DB PASSWD : ");  userPwd = scan.next();


    PolicyDao dao = new PolicyDao();
    DbConnection beforeDb = new PostConnection();
    String query = "select dest_ip, dest_port, dest_port2, svc_mod, src_ip from tbl_hira_a";
    List<PolicyDto> beforeDtos = dao.getPolicy(beforeDb.connection(ip, port, dbName, userId, userPwd), query);

    System.out.print("신규 DB IP : ");    ip = scan.next();
    System.out.print("신규 DB PORT : ");  port = scan.next();
    System.out.print("신규 DB DB명 : ");  dbName = scan.next();
    System.out.print("신규 DB ID : ");    userId = scan.next();
    System.out.print("신규 DB PASSWD : ");  userPwd = scan.next();

    DbConnection newDb = new PostConnection();
    String query2 = "select dest_ip, dest_port, dest_port2, svc_mod, src_ip from tbl_hira_b";
    List<PolicyDto> afterDtos = dao.getPolicy(newDb.connection(ip, port, dbName, userId, userPwd), query2);

    scan.close();

    System.out.println("==== 이전DB 데이터건수 : " + beforeDtos.size());
    System.out.println("==== 신규DB 데이터건수 : " + afterDtos.size());

    compareDtos(beforeDtos, afterDtos);
  }

  private static void compareDtos(List<PolicyDto> beforeDtos, List<PolicyDto> tmpDtos){

    List<PolicyDto> targetDto = DataConverter.dataSplit(tmpDtos);
    Map<String, Map<String, List<Integer>>> map = DataConverter.dataMap(targetDto);

    for(PolicyDto dto : beforeDtos){
      if(!commpare(dto, map)){
        System.out.println("미존재 : " + dto);
      }
    }
  }

  private static boolean commpare(PolicyDto dto, Map<String, Map<String, List<Integer>>> map){
    //key 검색
    if(map.containsKey(dto.getKey())){
      Map<String, List<Integer>> destMap = map.get(dto.getKey());
      //destIp 검색
      if(destMap.containsKey(dto.getDestIp())){
        List<Integer> portList = destMap.get(dto.getDestIp());
        //destPort 검색
        if(portList.contains(dto.getDestPort()))  return true;
      }
    }

    return false;
  }
}
