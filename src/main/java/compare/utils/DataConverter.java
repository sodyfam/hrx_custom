package compare.utils;

import compare.dao.PolicyDao;
import compare.domain.PolicyDto;

import java.util.*;
import java.util.stream.Collectors;

public class DataConverter {

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

  public static Map<String, List<Integer>> dataMap(List<PolicyDto> dtos){
    Map<String, List<Integer>> map = new HashMap<>();
    for(PolicyDto dto : dtos){
      System.out.println("======" + dto.toString());
      if(map.containsKey(dto.getKey())){
        List<Integer> data = map.get(dto.getKey());
        data.add(dto.getDestPort());
        map.put(dto.getKey(), data);
      }else{
        map.put(dto.getKey(), new ArrayList<>(){{add(dto.getDestPort());}});
      }
    }

    return map;
  }
}
