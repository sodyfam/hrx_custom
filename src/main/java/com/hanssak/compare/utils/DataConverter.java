package com.hanssak.compare.utils;

import com.hanssak.compare.domain.PolicyDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataConverter {

  /*
  port rage -> 개별로 분할
   */
  public static List<PolicyDto> dataSplit(List<PolicyDto> dtos){
    List<PolicyDto> newDtos = new ArrayList<>();

    dtos.forEach(d -> {
      if(d.isRange()) {
        try {
          for(int i=d.getDestPort(); i<=d.getDestPort2(); i++){
            PolicyDto newDto = (PolicyDto) d.clone();
            newDto.changePort(i);
            newDtos.add(newDto);
          }
        } catch (CloneNotSupportedException e) {
          e.printStackTrace();
        }
      }else{
        newDtos.add(d);
      }
    });

    return newDtos;
  }

  /*
  Map<scrIp+svcMod, Map<dertIp, List<port>>>
   */
  public static Map<String, Map<String, List<Integer>>> dataMap(List<PolicyDto> dtos){
    Map<String, Map<String, List<Integer>>> map = new HashMap<>();

    for(PolicyDto dto : dtos){
      if(map.containsKey(dto.getKey())){
        Map<String, List<Integer>> destMap = map.get(dto.getKey());
        List<Integer> list = new ArrayList<>();
        if(destMap.containsKey(dto.getDestIp())){
          list = destMap.get(dto.getDestIp());
        }
        list.add(dto.getDestPort());
        destMap.put(dto.getDestIp(), list);
      }else{
        Map<String, List<Integer>> tmpMap = new HashMap<>();
        tmpMap.put(dto.getDestIp(), new ArrayList<>(){{add(dto.getDestPort());}});
        map.put(dto.getKey(), tmpMap);
      }
    }

/*    Map<String, List<Integer>> map = new HashMap<>();
    for(PolicyDto dto : dtos){
      System.out.println("======" + dto.toString());
      if(map.containsKey(dto.getKey())){
        List<Integer> data = map.get(dto.getKey());
        data.add(dto.getDestPort());
        map.put(dto.getKey(), data);
      }else{
        map.put(dto.getKey(), new ArrayList<>(){{add(dto.getDestPort());}});
      }
    }*/

    return map;
  }
}
