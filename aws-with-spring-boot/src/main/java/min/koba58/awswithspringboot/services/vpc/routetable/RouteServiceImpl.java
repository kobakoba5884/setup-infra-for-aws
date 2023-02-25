package min.koba58.awswithspringboot.services.vpc.routetable;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateRouteRequest;
import software.amazon.awssdk.services.ec2.model.CreateRouteResponse;
import software.amazon.awssdk.services.ec2.model.DeleteRouteResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final Ec2Client ec2Client;

    @Override
    public CreateRouteResponse createRouteTargetedInternetGateway(String routeTableId, String cidrBlock,
            String internetGatewayId) throws Ec2Exception {
        CreateRouteRequest createRouteRequest = CreateRouteRequest.builder()
                .routeTableId(routeTableId)
                .destinationCidrBlock(cidrBlock)
                .gatewayId(internetGatewayId)
                .build();

        try {
            CreateRouteResponse createRouteResponse = ec2Client.createRoute(createRouteRequest);

            System.out.println("successfully created route in route table (%s)".formatted(routeTableId));

            return createRouteResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public DeleteRouteResponse deleteRoute(String routeId) throws Ec2Exception {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRoute'");
    }

}
