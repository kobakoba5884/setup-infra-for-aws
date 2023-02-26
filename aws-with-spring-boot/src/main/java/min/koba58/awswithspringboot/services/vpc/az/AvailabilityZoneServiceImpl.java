package min.koba58.awswithspringboot.services.vpc.az;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AvailabilityZone;
import software.amazon.awssdk.services.ec2.model.DescribeAvailabilityZonesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Service
@RequiredArgsConstructor
public class AvailabilityZoneServiceImpl implements AvailabilityZoneService{
    private final Ec2Client ec2Client;

    @Override
    public List<String> getAvailabilityZoneNames() throws Ec2Exception {
        try{
            List<AvailabilityZone> availabilityZones = getAvailabilityZone();

            availabilityZones.forEach(System.out::println);

            return availabilityZones.stream().map(availabilityZone -> availabilityZone.zoneName()).toList();
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public String getNetworkBorderGroup(){
        List<AvailabilityZone> availabilityZones = getAvailabilityZone();

        return availabilityZones.get(0).networkBorderGroup();
    }

    @Override
    public List<AvailabilityZone> getAvailabilityZone() throws Ec2Exception {
        try{
            DescribeAvailabilityZonesResponse availabilityZonesResponse = ec2Client.describeAvailabilityZones();

            List<AvailabilityZone> availabilityZones = availabilityZonesResponse.availabilityZones();

            availabilityZones.forEach(System.out::println);

            return availabilityZones;
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }
}
