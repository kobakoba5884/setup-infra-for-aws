package min.koba58.awswithspringboot.services.vpc.routetable;

import software.amazon.awssdk.services.ec2.model.CreateRouteResponse;
import software.amazon.awssdk.services.ec2.model.DeleteRouteResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface RouteService {
    CreateRouteResponse createRouteTargetedInternetGateway(String routeTableId, String cidrBlock, String internetGatewayId) throws Ec2Exception;

    DeleteRouteResponse deleteRoute(String routeId) throws Ec2Exception;
}
