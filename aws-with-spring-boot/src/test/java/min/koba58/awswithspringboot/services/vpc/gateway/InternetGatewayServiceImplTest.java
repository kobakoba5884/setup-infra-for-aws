package min.koba58.awswithspringboot.services.vpc.gateway;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;
import software.amazon.awssdk.services.ec2.model.AttachInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.CreateInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DeleteInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DetachInternetGatewayResponse;

public class InternetGatewayServiceImplTest extends VpcSharedTest {
    @Test
    void testCreateInternetGateway() {
        CreateInternetGatewayResponse result = internetGatewayService.createInternetGateway(internetGatewayName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDeleteInternetGatewayByName() {
        DeleteInternetGatewayResponse result = internetGatewayService.deleteInternetGatewayByName(internetGatewayName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testAttachInternetGatewayToVpc() {
        String vpcId = vpcService.getVpcIdByName(vpcName);
        String internetGatewayId = internetGatewayService.getInternetGatewayIdByName(internetGatewayName);

        AttachInternetGatewayResponse result = internetGatewayService.attachInternetGatewayToVpc(vpcId,
                internetGatewayId);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDetachInternetGatewayFromVpc() {
        String vpcId = vpcService.getVpcIdByName(vpcName);
        String internetGatewayId = internetGatewayService.getInternetGatewayIdByName(internetGatewayName);

        DetachInternetGatewayResponse result = internetGatewayService.detachInternetGatewayFromVpc(vpcId,
                internetGatewayId);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testGetInternetGatewayByName_normal() {
        assertDoesNotThrow(() -> {
            internetGatewayService.getInternetGatewayIdByName(internetGatewayName);
        });
    }
}
