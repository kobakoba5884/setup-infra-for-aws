package min.koba58.awswithspringboot.services.vpc.subnet;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

public class SubnetServiceImplTest extends VpcSharedTest{
    @Test
    void testCreateSubnet() {
        @SuppressWarnings("unused")
        TagSpecification tagSpecification = ec2TagService.buildNameSpecification(internetGatewayName, ResourceType.SUBNET);
    
        
    }
}
