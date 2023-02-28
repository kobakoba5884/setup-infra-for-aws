package min.koba58.awswithspringboot.services.network;

import min.koba58.awswithspringboot.dtos.CreateMultiAZDto;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface BuildNetworkService {
    void createVpcForBasicMultiAZ(CreateMultiAZDto dto) throws Ec2Exception;
}
