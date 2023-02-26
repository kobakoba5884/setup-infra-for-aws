package min.koba58.awswithspringboot.services.vpc.elasticIp;

import software.amazon.awssdk.services.ec2.model.AllocateAddressResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface ElasticIpService {
    AllocateAddressResponse allocateElasticIp(String elasticIpName) throws Ec2Exception;
}
