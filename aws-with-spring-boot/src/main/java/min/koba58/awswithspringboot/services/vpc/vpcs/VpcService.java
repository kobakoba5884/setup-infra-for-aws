package min.koba58.awswithspringboot.services.vpc.vpcs;

import java.util.List;

import software.amazon.awssdk.services.ec2.model.DeleteVpcResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Vpc;

public interface VpcService {
    String createVpc(String vpcName, String sidrBlock) throws Ec2Exception;

    DeleteVpcResponse deleteVpcById(String vpcId) throws Ec2Exception;

    DeleteVpcResponse deleteVpcByName(String vpcName) throws Ec2Exception;

    String getVpcIdByName(String vpcName) throws Ec2Exception;

    List<Vpc> getVpcs() throws Ec2Exception;
}
