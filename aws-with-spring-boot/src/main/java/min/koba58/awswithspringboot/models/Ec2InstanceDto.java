package min.koba58.awswithspringboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.ec2.model.InstanceType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ec2InstanceDto {
    private String amiId = "ami-072bfb8ae2c884cc4";
    private InstanceType instanceType = InstanceType.T2_MICRO;
    private Integer createMaxCount = 1;
    private Integer createMinCount = 1;
    private String keyPairName = "key-for-default";
    private String securityGroupName = "default-security-group";
    private String securityGroupDesc = "default-security-group-for-desc";
    private String tagName = "default-instance";
}
