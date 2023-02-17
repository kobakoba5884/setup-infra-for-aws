package min.koba58.awswithspringboot.services.ec2;

import min.koba58.awswithspringboot.models.Ec2InstanceDto;

public interface Ec2InstanceService {
    String createEc2Instance(Ec2InstanceDto ec2InstanceDto);
}
