package min.koba58.awswithspringboot.services.vpc.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AttachInternetGatewayRequest;
import software.amazon.awssdk.services.ec2.model.AttachInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.CreateInternetGatewayRequest;
import software.amazon.awssdk.services.ec2.model.CreateInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DeleteInternetGatewayRequest;
import software.amazon.awssdk.services.ec2.model.DeleteInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.DescribeInternetGatewaysRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInternetGatewaysResponse;
import software.amazon.awssdk.services.ec2.model.DetachInternetGatewayRequest;
import software.amazon.awssdk.services.ec2.model.DetachInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.InternetGateway;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

@Service
@RequiredArgsConstructor
public class InternetGatewayServiceImpl implements InternetGatewayService {
    private final Ec2Client ec2Client;

    @Override
    public CreateInternetGatewayResponse createInternetGateway(String internetGatewayName) throws Ec2Exception {
        Tag tag = Tag.builder()
                .key("Name")
                .value(internetGatewayName)
                .build();

        TagSpecification tagSpecification = TagSpecification.builder()
                .resourceType(ResourceType.INTERNET_GATEWAY)
                .tags(tag)
                .build();

        CreateInternetGatewayRequest createInternetGatewayRequest = CreateInternetGatewayRequest.builder()
                .tagSpecifications(tagSpecification)
                .build();
        try {
            CreateInternetGatewayResponse createInternetGatewayResponse = ec2Client
                    .createInternetGateway(createInternetGatewayRequest);

            InternetGateway internetGateway = createInternetGatewayResponse.internetGateway();

            String igwId = internetGateway.internetGatewayId();

            waitUntilIgwExists(igwId);

            System.out.printf("Created Internet Gateway with ID %s\n", igwId);
            System.out.println(internetGatewayName);

            return createInternetGatewayResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    private void waitUntilIgwExists(String igwId) throws Ec2Exception {
        DescribeInternetGatewaysRequest describeInternetGatewaysRequest = DescribeInternetGatewaysRequest.builder()
                .internetGatewayIds(igwId)
                .build();

        System.out.println("be figuring out if it was created.");

        WaiterResponse<DescribeInternetGatewaysResponse> waiter = ec2Client.waiter()
                .waitUntilInternetGatewayExists(describeInternetGatewaysRequest);

        Optional<DescribeInternetGatewaysResponse> response = waiter.matched().response();

        if (response.isEmpty()) {
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                    .errorMessage("waitUntilInternetGatewayExists method is failed.").build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }

        System.out.println("internet gateway (%s) is existed!.".formatted(igwId));
    }

    @Override
    public DeleteInternetGatewayResponse deleteInternetGatewayById(String internetGatewayId) throws Ec2Exception {
        DeleteInternetGatewayRequest request = DeleteInternetGatewayRequest.builder()
                .internetGatewayId(internetGatewayId)
                .build();
        try {
            DeleteInternetGatewayResponse deleteInternetGatewayResponse = ec2Client.deleteInternetGateway(request);

            System.out.printf("Deleted Internet Gateway with ID %s\n", internetGatewayId);

            return deleteInternetGatewayResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public String getInternetGatewayIdByName(String internetGatewayName) throws Ec2Exception {
        InternetGateway internetGateway = getInternetGatewayByName(internetGatewayName);

        return internetGateway.internetGatewayId();
    }

    @Override
    public InternetGateway getInternetGatewayByName(String internetGatewayName) throws Ec2Exception {
        Filter nameFilter = Filter.builder()
                .name("tag:Name")
                .values(internetGatewayName)
                .build();

        DescribeInternetGatewaysRequest describeInternetGatewaysRequest = DescribeInternetGatewaysRequest.builder()
                .filters(nameFilter)
                .build();

        try {
            DescribeInternetGatewaysResponse describeInternetGatewaysResponse = ec2Client
                    .describeInternetGateways(describeInternetGatewaysRequest);

            List<InternetGateway> internetGateways = describeInternetGatewaysResponse.internetGateways();

            if (internetGateways.isEmpty()) {
                AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                        .errorMessage("not found internet gateway (%s)".formatted(internetGatewayName)).build();

                throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
            } else if (internetGateways.size() > 1) {
                AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                        .errorMessage("It don't know which one %s is because there is more than one."
                                .formatted(internetGatewayName))
                        .build();

                throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
            }

            return internetGateways.get(0);
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public DeleteInternetGatewayResponse deleteInternetGatewayByName(String internetGatewayName) throws Ec2Exception {
        String internetGatewayId = getInternetGatewayIdByName(internetGatewayName);

        return deleteInternetGatewayById(internetGatewayId);
    }

    @Override
    public AttachInternetGatewayResponse attachInternetGatewayToVpc(String vpcId, String internetGatewayId)
            throws Ec2Exception {
        AttachInternetGatewayRequest attachClassicLinkVpcRequest = AttachInternetGatewayRequest.builder()
                .vpcId(vpcId)
                .internetGatewayId(internetGatewayId)
                .build();

        try {
            AttachInternetGatewayResponse attachInternetGatewayResponse = ec2Client
                    .attachInternetGateway(attachClassicLinkVpcRequest);

            System.out.println(
                    "successfully attached internet gateway (%s) to vpc (%s)".formatted(internetGatewayId, vpcId));

            return attachInternetGatewayResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    @Override
    public DetachInternetGatewayResponse detachInternetGatewayFromVpc(String vpcId, String internetGatewayId)
            throws Ec2Exception {
        DetachInternetGatewayRequest detachInternetGatewayRequest = DetachInternetGatewayRequest.builder()
                .vpcId(vpcId)
                .internetGatewayId(internetGatewayId)
                .build();

        try {
            DetachInternetGatewayResponse detachInternetGatewayResponse = ec2Client
                    .detachInternetGateway(detachInternetGatewayRequest);

            System.out.println(
                    "successfully detached internet gateway (%s) from vpc (%s)".formatted(internetGatewayId, vpcId));

            return detachInternetGatewayResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

}
