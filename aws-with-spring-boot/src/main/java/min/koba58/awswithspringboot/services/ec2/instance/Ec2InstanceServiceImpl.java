package min.koba58.awswithspringboot.services.ec2.instance;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.models.Ec2InstanceDto;
import min.koba58.awswithspringboot.services.ec2.keyPair.Ec2KeyPairService;
import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;
import software.amazon.awssdk.services.ec2.model.RunInstancesRequest;
import software.amazon.awssdk.services.ec2.model.RunInstancesResponse;

@Service
@RequiredArgsConstructor
public class Ec2InstanceServiceImpl implements Ec2InstanceService{
    private final Ec2Client ec2Client;

    // private final Ec2SecurityGroupService securityGroupService;

    private final Ec2KeyPairService keyPairService;

    private final Ec2TagService tagService;

    // create ec2 instance
    @Override
    public String createEc2Instance(Ec2InstanceDto ec2InstanceDto){
        prepareBeforeCreating(ec2InstanceDto);

        String amiId = ec2InstanceDto.getAmiId();

        RunInstancesRequest runInstancesRequest = RunInstancesRequest.builder()
            .imageId(amiId)
            .instanceType(ec2InstanceDto.getInstanceType())
            .maxCount(ec2InstanceDto.getCreateMaxCount())
            .minCount(ec2InstanceDto.getCreateMinCount())
            .keyName(ec2InstanceDto.getKeyPairName())
            .securityGroups(ec2InstanceDto.getSecurityGroupName())
            .build();

        try{
            RunInstancesResponse runInstancesResponse = ec2Client.runInstances(runInstancesRequest);
            String instanceId = runInstancesResponse.instances().get(0).instanceId();

            tagService.createTag(instanceId, ec2InstanceDto.getTagName());

            System.out.format("Successfully started EC2 Instance %s based on AMI %s\n", instanceId, amiId);
            
            return instanceId;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return "";
        }
    }

    private void prepareBeforeCreating(Ec2InstanceDto ec2InstanceDto){
        @SuppressWarnings("unused")
        String securityGroupName = ec2InstanceDto.getSecurityGroupName();

        // todo
        // SecurityGroup securityGroup = securityGroupService.getEc2SecurityGroupByName(securityGroupName);

        // if(securityGroup.isEmpty()){
        //     securityGroupService.createEc2SecurityGroup(securityGroupName, securityGroupName, DEFAULT_VPC_ID);
        // }

        String keyPairName = ec2InstanceDto.getKeyPairName();

        Optional<KeyPairInfo> keyPairInfo = keyPairService.getEc2KeyByKeyName(keyPairName);

        keyPairInfo.ifPresent(key -> keyPairService.deleteKeyPair(keyPairName));
        keyPairService.createEc2KeyPair(keyPairName);
    }
}
