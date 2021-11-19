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

    for(PolicyDto d : dtos){
//      if(d.checkIpRange()) {
        try {
          for(int i=d.getDestPort(); i<=d.getDestPort2(); i++){
            if(d.checkDestIdRange()){
              PolicyDto newDto = (PolicyDto) d.clone();
              newDto.changePort(i);
              for(int ip=0; ip<254; ip++){
                PolicyDto destCloneDto = (PolicyDto) newDto.clone();
                destCloneDto.changeDestListIp(ip);
                newDtos.add(destCloneDto);
              }
            }else{
              PolicyDto newDto = (PolicyDto) d.clone();
              newDto.changePort(i);
              newDtos.add(newDto);
            }
          }
        } catch (CloneNotSupportedException e) {
          e.printStackTrace();
        }
//      }else{
//        newDtos.add(d);
//      }
    }

    return newDtos;
  }

  private List<PolicyDto> parserRange(PolicyDto dto) {
    if(dto.checkSrcIpRange() || dto.checkDestIdRange() || dto.getDestPort() != dto.getDestPort2()){
      List<PolicyDto> list = new ArrayList<PolicyDto>();
      try {
        for (int i = dto.getDestPort(); i <= dto.getDestPort2(); i++) {
          if (dto.checkDestIdRange() && dto.checkSrcIpRange()) {
            PolicyDto newDto = (PolicyDto) dto.clone();
            newDto.changePort(i);
            for (int destIp = 0; destIp < 254; destIp++) {
              PolicyDto destCloneDto = (PolicyDto) newDto.clone();
              destCloneDto.changeDestListIp(destIp);
              list.add(destCloneDto);

              for (int srcIp = 0; srcIp < 254; srcIp++) {
                PolicyDto srcCloneDto = (PolicyDto) destCloneDto.clone();
                srcCloneDto.changeDestListIp(srcIp);
                list.add(srcCloneDto);
              }
            }
          }else if(dto.checkDestIdRange() && !dto.checkSrcIpRange()){
            PolicyDto newDto = (PolicyDto) dto.clone();
            newDto.changePort(i);
            for (int destIp = 0; destIp < 254; destIp++) {
              PolicyDto destCloneDto = (PolicyDto) newDto.clone();
              destCloneDto.changeDestListIp(destIp);
              list.add(destCloneDto);
            }
          }else if(dto.checkDestIdRange() && dto.checkSrcIpRange()){
            PolicyDto newDto = (PolicyDto) dto.clone();
            newDto.changePort(i);
            for (int srcIp = 0; srcIp < 254; srcIp++) {
              PolicyDto srcCloneDto = (PolicyDto) newDto.clone();
              srcCloneDto.changeDestListIp(srcIp);
              list.add(srcCloneDto);
            }
          } else {
            PolicyDto newDto = (PolicyDto) dto.clone();
            newDto.changePort(i);
            list.add(newDto);
          }
        }
      }catch(CloneNotSupportedException e){
        e.printStackTrace();
      }

      return list;
    }else{
      return new ArrayList<PolicyDto>(){{add(dto);}};
    }
  }

  /*
  Map<scrIp+svcMod, Map<dertIp, List<port>>>
   */
  public static Map<String, Map<String, List<Integer>>> dataMap(List<PolicyDto> dtos){
    Map<String, Map<String, List<Integer>>> map = new HashMap<>();

    for(PolicyDto dto : dtos){
      if(map.containsKey(dto.getKey())){
        Map<String, List<Integer>> destMap = map.get(dto.getKey());
        List<Integer> list = new ArrayList<Integer>();
        if(destMap.containsKey(dto.getDestIp())){
          list = destMap.get(dto.getDestIp());
        }
        list.add(dto.getDestPort());
        destMap.put(dto.getDestIp(), list);
      }else{
        Map<String, List<Integer>> tmpMap = new HashMap<>();
        tmpMap.put(dto.getDestIp(), new ArrayList<Integer>(){{add(dto.getDestPort());}});
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
