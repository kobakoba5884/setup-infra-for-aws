package min.koba58.awswithspringboot.services.network;

import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface BuildNetworkService {
    void createVpcForBasicMultiAZ(String vpcName, String sidrBlockForVpc) throws Ec2Exception;
}
