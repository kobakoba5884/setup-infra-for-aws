package min.koba58.awswithspringboot.services.vpc.vpcs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;
import software.amazon.awssdk.services.ec2.model.DeleteVpcResponse;

public class VpcServiceImplTest extends VpcSharedTest {
    @Test
    public void createVpcTest_normal() {
        String vpcId = vpcService.createVpc(vpcName, sidrBlock);

        assertFalse(vpcId.isEmpty());
    }

    @Test
    void testDeleteVpcByName_normal() {
        DeleteVpcResponse result = vpcService.deleteVpcByName(vpcName);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
