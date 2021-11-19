package com.hanssak.compare.domain;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDto implements Cloneable {

  private String destIp;
  private Integer destPort;
  private Integer destPort2;
  private String svcMod;
  private String srcIp;
  private String portRange;

  public boolean isRange(){
    return destPort < destPort2;
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public void changePort(int port){
    this.destPort = port;
    this.destPort2 = port;
  }

  public String getKey(){
    return srcIp +"."+ svcMod;
  }

  public void setPortRange(String portRange){
    this.portRange = portRange;
  }

  public void changeDestListIp(int ip){
    int index = destIp.lastIndexOf(".");
    destIp = destIp.substring(0, index+1) + ip;
  }

  private boolean checkIpRange(String ip){
    int index = ip.lastIndexOf(".");
    return "0".equals(ip.substring(index+1));
  }
  public boolean checkSrcIpRange() {
    return checkIpRange(srcIp);
  }
  public boolean checkDestIdRange() {
    return checkIpRange(destIp);
  }
  

  public int getDestPort2(){
    return destPort2 == 0 ? destPort : destPort2;
  }

  
}
