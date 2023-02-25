package min.koba58.awswithspringboot.services.vpc.az;

import java.util.List;

import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface AvailabilityZoneService {
    List<String> getAvailabilityZoneNames() throws Ec2Exception;

}
