package min.koba58.awswithspringboot.services.vpc.gateway;

import software.amazon.awssdk.services.ec2.model.CreateNatGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DeleteNatGatewayResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.NatGateway;

public interface NatGatewayService {
    CreateNatGatewayResponse createNatGatewayForPublic(String natGatewayName, String subnetId, String allocatedId) throws Ec2Exception;

    DeleteNatGatewayResponse deleteNatGatewayById(String natGatewayId) throws Ec2Exception;

    DeleteNatGatewayResponse deleteNatGatewayByName(String natGatewayName) throws Ec2Exception;

    String getNatGatewayIdByName(String natGatewayName) throws Ec2Exception;

    NatGateway getNatGatewayByName(String natGatewayName) throws Ec2Exception;

    // AttachNatGatewayResponse attachNatGatewayToVpc(String vpcId, String natGatewayId) throws Ec2Exception;

    // DetachInternetGatewayResponse detachInternetGatewayFromVpc(String vpcId, String natGatewayId) throws Ec2Exception;
}
