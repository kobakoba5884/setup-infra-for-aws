package min.koba58.awswithspringboot.services.vpc.routetable;

import software.amazon.awssdk.services.ec2.model.CreateRouteTableResponse;
import software.amazon.awssdk.services.ec2.model.DeleteRouteTableResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface RouteTableService {
    CreateRouteTableResponse createRouteTable(String routeTableName, String vpcId) throws Ec2Exception;

    DeleteRouteTableResponse deleteRouteTableById(String routeTableId) throws Ec2Exception;

    String getRouteTableIdByName(String routeName) throws Ec2Exception;
}
