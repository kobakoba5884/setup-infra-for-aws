package min.koba58.awswithspringboot.services.vpc.routetable;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;
import software.amazon.awssdk.services.ec2.model.DeleteRouteTableResponse;

public class RouteTableServiceImplTest extends VpcSharedTest {
    @Test
    void testCreateRouteTable() {
        String vpcId = vpcService.getVpcIdByName(vpcName);

        String result = routeTableService.createRouteTable(routeTableName, vpcId);

        assertTrue(!result.isEmpty());
    }

    @Test
    void testDeleteRouteTableById() {
        String routeTableId = routeTableService.getRouteTableIdByName(routeTableName);

        DeleteRouteTableResponse result = routeTableService.deleteRouteTableById(routeTableId);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
