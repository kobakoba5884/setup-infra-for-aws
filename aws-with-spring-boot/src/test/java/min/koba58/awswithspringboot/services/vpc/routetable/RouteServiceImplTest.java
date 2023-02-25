package min.koba58.awswithspringboot.services.vpc.routetable;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;
import software.amazon.awssdk.services.ec2.model.CreateRouteResponse;

public class RouteServiceImplTest extends VpcSharedTest {
    @Test
    void testCreateRouteTargetedInternetGateway() {
        String routeTableId = routeTableService.getRouteTableIdByName(routeTableName);

        String internetGatewayId = internetGatewayService.getInternetGatewayIdByName(internetGatewayName);

        CreateRouteResponse result = routeService.createRouteTargetedInternetGateway(routeTableId,
                sidrBlockForRouteAboutIGW, internetGatewayId);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDeleteRoute() {

    }
}
