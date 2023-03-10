package min.koba58.awswithspringboot.services.vpc.subnet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;
import software.amazon.awssdk.services.ec2.model.CreateSubnetResponse;
import software.amazon.awssdk.services.ec2.model.DeleteSubnetResponse;

public class SubnetServiceImplTest extends VpcSharedTest{
    @Test
    void testCreateSubnet() {
        String vpcId = vpcService.getVpcIdByName(vpcName);
    
        CreateSubnetResponse result = subnetService.createSubnet(subnetName, sidrBlockForSubnet, vpcId, availabilityZoneName);
    
        assertTrue(result.sdkHttpResponse().isSuccessful());
    }

    @Test
    void testDeleteSubnet() {
        String subnetId = subnetService.getSubnetIdByName(subnetName);

        DeleteSubnetResponse result = subnetService.deleteSubnet(subnetId);

        assertTrue(result.sdkHttpResponse().isSuccessful());
    }
}
