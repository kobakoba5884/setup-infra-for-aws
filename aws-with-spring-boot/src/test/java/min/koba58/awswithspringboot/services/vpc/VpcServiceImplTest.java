package min.koba58.awswithspringboot.services.vpc;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class VpcServiceImplTest extends VpcSharedTest {
    @Test
    public void createVpcTest() {
        String vpcId = vpcService.createVpc(vpcName);

        assertFalse(vpcId.isEmpty());
    }
}
