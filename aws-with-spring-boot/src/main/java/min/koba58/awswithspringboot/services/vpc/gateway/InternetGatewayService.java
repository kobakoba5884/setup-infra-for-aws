package min.koba58.awswithspringboot.services.vpc.gateway;

import software.amazon.awssdk.services.ec2.model.AttachInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.CreateInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DeleteInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DetachInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.InternetGateway;

public interface InternetGatewayService {
    CreateInternetGatewayResponse createInternetGateway(String internetGatewayName) throws Ec2Exception;

    DeleteInternetGatewayResponse deleteInternetGatewayById(String internetGatewayId) throws Ec2Exception;

    DeleteInternetGatewayResponse deleteInternetGatewayByName(String internetGatewayName) throws Ec2Exception;

    String getInternetGatewayIdByName(String internetGatewayName) throws Ec2Exception;

    InternetGateway getInternetGatewayByName(String internetGatewayName) throws Ec2Exception;

    AttachInternetGatewayResponse attachInternetGatewayToVpc(String vpcId, String internetGatewayId) throws Ec2Exception;

    DetachInternetGatewayResponse detachInternetGatewayFromVpc(String vpcId, String internetGatewayId) throws Ec2Exception;
}
