package min.koba58.awswithspringboot.services.vpc.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import min.koba58.awswithspringboot.utils.SharedHandler;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateNatGatewayRequest;
import software.amazon.awssdk.services.ec2.model.CreateNatGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DeleteNatGatewayRequest;
import software.amazon.awssdk.services.ec2.model.DeleteNatGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DescribeNatGatewaysRequest;
import software.amazon.awssdk.services.ec2.model.DescribeNatGatewaysResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.NatGateway;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

@Service
@RequiredArgsConstructor
public class NatGatewayServiceImpl implements NatGatewayService {
    private Ec2Client ec2Client;

    private Ec2TagService ec2TagService;

    private final SharedHandler sharedHandler;

    @Override
    public CreateNatGatewayResponse createNatGatewayForPublic(String natGatewayName, String subnetId, String allocatedId)
            throws Ec2Exception {
        TagSpecification tagSpecification = ec2TagService.buildNameTagSpecification(natGatewayName,
                ResourceType.NATGATEWAY);

        CreateNatGatewayRequest createNatGatewayRequest = CreateNatGatewayRequest.builder()
                .tagSpecifications(tagSpecification)
                .subnetId(subnetId)
                .allocationId(allocatedId)
                .build();

        try {
            CreateNatGatewayResponse createNatGatewayResponse = ec2Client.createNatGateway(createNatGatewayRequest);

            NatGateway natGateway = createNatGatewayResponse.natGateway();

            String natGatewayId = natGateway.natGatewayId();

            waitUntilNatGatewayAvailable(natGatewayId);

            System.out.printf("Created Internet Gateway with ID %s\n", natGatewayId);
            System.out.println(natGateway);


            return createNatGatewayResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    private void waitUntilNatGatewayAvailable(String natGatewayId) throws Ec2Exception {
        DescribeNatGatewaysRequest describeNatGatewaysRequest = DescribeNatGatewaysRequest.builder()
                .natGatewayIds(natGatewayId)
                .build();

        System.out.println("be figuring out if it is available.");

        WaiterResponse<DescribeNatGatewaysResponse> waiter = ec2Client.waiter()
                .waitUntilNatGatewayAvailable(describeNatGatewaysRequest);

        Optional<DescribeNatGatewaysResponse> response = waiter.matched().response();

        if (response.isEmpty()) {
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                    .errorMessage("waitUntilNatGatewayAvailable method is failed.").build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }

        System.out.println("nat gateway (%s) is available!.".formatted(natGatewayId));
    }

    @Override
    public DeleteNatGatewayResponse deleteNatGatewayById(String natGatewayId) throws Ec2Exception {
        DeleteNatGatewayRequest request = DeleteNatGatewayRequest.builder()
                .natGatewayId(natGatewayId)
                .build();
        try {
            DeleteNatGatewayResponse deleteNatGatewayResponse = ec2Client.deleteNatGateway(request);

            System.out.printf("Deleted nat Gateway with ID %s\n", natGatewayId);

            return deleteNatGatewayResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public DeleteNatGatewayResponse deleteNatGatewayByName(String natGatewayName) throws Ec2Exception {
        String natGatewayId = getNatGatewayIdByName(natGatewayName);

        return deleteNatGatewayById(natGatewayId);
    }

    @Override
    public String getNatGatewayIdByName(String natGatewayName) throws Ec2Exception {
        NatGateway natGateway = getNatGatewayByName(natGatewayName);

        return natGateway.natGatewayId();
    }

    @Override
    public NatGateway getNatGatewayByName(String natGatewayName) throws Ec2Exception {
        Filter nameFilter = ec2TagService.buildFilterName(natGatewayName);

        DescribeNatGatewaysRequest describeNatGatewaysRequest = DescribeNatGatewaysRequest.builder()
                .filter(nameFilter)
                .build();

        try {
            DescribeNatGatewaysResponse describeNatGatewaysResponse = ec2Client
                    .describeNatGateways(describeNatGatewaysRequest);

            List<NatGateway> natGateways = describeNatGatewaysResponse.natGateways();

            sharedHandler.verifyIfResponseIsCorrect(natGateways, natGatewayName);

            return natGateways.get(0);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

}
