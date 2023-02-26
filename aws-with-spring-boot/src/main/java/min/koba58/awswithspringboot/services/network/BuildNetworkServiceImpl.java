package min.koba58.awswithspringboot.services.network;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.vpc.az.AvailabilityZoneService;
import min.koba58.awswithspringboot.services.vpc.vpcs.VpcService;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Service
@RequiredArgsConstructor
public class BuildNetworkServiceImpl implements BuildNetworkService{
    private final VpcService vpcService;

    private final AvailabilityZoneService availabilityZoneService;

    @Override
    public void createVpcForBasicMultiAZ(String vpcName, String sidrBlockForVpc) throws Ec2Exception {
        @SuppressWarnings("unused")
        String vpcId = vpcService.createVpc(vpcName, sidrBlockForVpc);

        List<String> availabilityZones = availabilityZoneService.getAvailabilityZoneNames();

        // create public subnet
        availabilityZones.stream().limit(2).forEach(availabilityZoneId -> {

        });
    }

    
}
