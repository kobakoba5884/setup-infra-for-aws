package min.koba58.awswithspringboot.services.network;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.dtos.CreateMultiAZDto;
import min.koba58.awswithspringboot.dtos.CreateSubnetDto;
import min.koba58.awswithspringboot.dtos.DeleteMultiAZDto;
import min.koba58.awswithspringboot.services.vpc.gateway.InternetGatewayService;
import min.koba58.awswithspringboot.services.vpc.subnet.SubnetService;
import min.koba58.awswithspringboot.services.vpc.vpcs.VpcService;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Service
@RequiredArgsConstructor
public class BuildNetworkServiceImpl implements BuildNetworkService{
    private final VpcService vpcService;

    private final SubnetService subnetService;

    private final InternetGatewayService internetGatewayService;

    @Override
    public void createVpcForBasicMultiAZ(CreateMultiAZDto dto) throws Ec2Exception {
        String vpcId = vpcService.createVpc(dto.getVpcName(), dto.getSidrBlockForVpc());

        List<CreateSubnetDto> subnetDtos = dto.getSubnetList();

        subnetDtos.stream().forEach(subnetDto -> {
            subnetService.createSubnet(subnetDto.getSubnetName(),
                                       subnetDto.getCidrBlockForSubnet(),
                                       vpcId, 
                                       subnetDto.getAvailabilityZoneName());
        });

        String internetGatewayId = internetGatewayService.createInternetGateway(dto.getInternetGatewayName());

        internetGatewayService.attachInternetGatewayToVpc(vpcId, internetGatewayId);
    }

    @Override
    public void deleteVpcForBasicMultiAZ(DeleteMultiAZDto dto) throws Ec2Exception {
        String vpcId = dto.getVpcId();
        String internetGatewayId = dto.getInternetGatewayId();

        internetGatewayService.detachInternetGatewayFromVpc(vpcId, internetGatewayId);

        internetGatewayService.deleteInternetGatewayById(internetGatewayId);

        List<String> subnetIds = dto.getSubnetIdList();

        subnetIds.stream().forEach(subnetId -> {
            subnetService.deleteSubnet(subnetId);
        });

        vpcService.deleteVpcById(vpcId);
    }
}
