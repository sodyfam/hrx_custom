package compare;

import compare.dao.PolicyDao;
import compare.domain.PolicyDto;
import compare.utils.DataConverter;
import compare.utils.DbConnection;
import compare.utils.PostConnection;
import lombok.extern.slf4j.Slf4j;

import java.awt.dnd.DropTarget;
import java.util.List;
import java.util.Map;

@Slf4j
public class CompareMgr {

  public static void main(String[] args) {

    PolicyDao dao = new PolicyDao();
    DbConnection postDb = new PostConnection();
    List<PolicyDto> beforeDtos = dao.getPolicy(postDb.connection());

    DbConnection mysqlDb = new PostConnection();
    List<PolicyDto> afterDtos = dao.getPolicy(mysqlDb.connection());


    System.out.println("==== beforeDtos : " + beforeDtos.size());
    System.out.println("==== afterDtos : " + afterDtos.size());

    compareDtos(beforeDtos, afterDtos);
  }

  private static void compareDtos(List<PolicyDto> beforeDtos, List<PolicyDto> tmpDtos){

    List<PolicyDto> targetDto = DataConverter.dataSplit(tmpDtos);
    Map<String, List<Integer>> map = DataConverter.dataMap(targetDto);


    for(PolicyDto bDto : beforeDtos){
      if(!map.containsKey(bDto.getKey())){  //미존재

      }else{  //port 체크
        List<Integer> ports = map.get(bDto.getKey());
        if(!ports.contains(bDto.getDestPort())){  //미존재

        }
      }
    }


    for(PolicyDto dto : targetDto){
      System.out.println(dto.toString());
    }

  }
}
