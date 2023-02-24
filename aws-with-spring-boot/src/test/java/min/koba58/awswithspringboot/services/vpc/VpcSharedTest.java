package min.koba58.awswithspringboot.services.vpc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import min.koba58.awswithspringboot.services.vpc.gateway.InternetGatewayService;
import min.koba58.awswithspringboot.services.vpc.vpcs.VpcService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VpcSharedTest {
    @Autowired
    protected VpcService vpcService;

    @Autowired
    protected InternetGatewayService internetGatewayService;

    @Autowired
    protected Ec2TagService ec2TagService;

    protected String vpcName = "test-vpc";

    protected String sidrBlock = "10.0.0.0/16";

    protected String internetGatewayName = "test-internet-gateway";
    
    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: %s".formatted(testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        System.out.println("%s method was end!!".formatted(testInfo.getDisplayName()));
    }
}
