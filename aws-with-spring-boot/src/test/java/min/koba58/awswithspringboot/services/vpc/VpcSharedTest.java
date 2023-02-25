package min.koba58.awswithspringboot.services.vpc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import min.koba58.awswithspringboot.services.vpc.az.AvailabilityZoneService;
import min.koba58.awswithspringboot.services.vpc.gateway.InternetGatewayService;
import min.koba58.awswithspringboot.services.vpc.routetable.RouteService;
import min.koba58.awswithspringboot.services.vpc.routetable.RouteTableService;
import min.koba58.awswithspringboot.services.vpc.subnet.SubnetService;
import min.koba58.awswithspringboot.services.vpc.vpcs.VpcService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VpcSharedTest {
    @Autowired
    protected VpcService vpcService;

    @Autowired
    protected InternetGatewayService internetGatewayService;

    @Autowired
    protected SubnetService subnetService;

    @Autowired
    protected AvailabilityZoneService availabilityZoneService;

    @Autowired
    protected RouteTableService routeTableService;

    @Autowired
    protected RouteService routeService;

    @Autowired
    protected Ec2TagService ec2TagService;

    protected String vpcName = "test-vpc";

    protected String sidrBlock = "10.0.0.0/16";

    protected String subnetName = "test-subnet";

    protected String sidrBlockForSubnet = "10.0.0.0/24";

    protected String availabilityZoneName ="ap-northeast-1a";

    protected String internetGatewayName = "test-internet-gateway";

    protected String routeTableName = "test-route-table";

    protected String sidrBlockForRouteAboutIGW = "0.0.0.0/0";
    
    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        System.out.println("Starting test: %s".formatted(testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        System.out.println("%s method was end!!".formatted(testInfo.getDisplayName()));
    }
}
