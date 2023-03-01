package min.koba58.awswithspringboot.services.vpc.routetable;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.ec2.tag.Ec2TagService;
import min.koba58.awswithspringboot.utils.SharedHandler;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AssociateRouteTableRequest;
import software.amazon.awssdk.services.ec2.model.AssociateRouteTableResponse;
import software.amazon.awssdk.services.ec2.model.CreateRouteTableRequest;
import software.amazon.awssdk.services.ec2.model.CreateRouteTableResponse;
import software.amazon.awssdk.services.ec2.model.DeleteRouteTableResponse;
import software.amazon.awssdk.services.ec2.model.DescribeRouteTablesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeRouteTablesResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.RouteTable;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

@Service
@RequiredArgsConstructor
public class RouteTableServiceImpl implements RouteTableService {
    private final Ec2Client ec2Client;

    private final Ec2TagService ec2TagService;

    private final SharedHandler sharedHandler;

    @Override
    public String createRouteTable(String routeTableName, String vpcId) throws Ec2Exception {
        TagSpecification tagSpecification = ec2TagService.buildNameTagSpecification(routeTableName,
                ResourceType.ROUTE_TABLE);

        CreateRouteTableRequest createRouteTableRequest = CreateRouteTableRequest.builder()
                .vpcId(vpcId)
                .tagSpecifications(tagSpecification)
                .build();

        try {
            CreateRouteTableResponse createRouteTableResponse = ec2Client.createRouteTable(createRouteTableRequest);

            RouteTable routeTable = createRouteTableResponse.routeTable();

            String routeTableId = routeTable.routeTableId();

            System.out.println("successfully created route table (%s:%s)".formatted(routeTableName, routeTableId));

            return routeTableId;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public DeleteRouteTableResponse deleteRouteTableById(String routeTableId) throws Ec2Exception {
        try {
            DeleteRouteTableResponse deleteRouteTableResponse = ec2Client
                    .deleteRouteTable(request -> request.routeTableId(routeTableId));

            System.out.println("successfully deleted route table (%s)".formatted(routeTableId));

            return deleteRouteTableResponse;
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public String getRouteTableIdByName(String routeName) throws Ec2Exception {
        Filter filter = ec2TagService.buildFilterName(routeName);

        DescribeRouteTablesRequest describeRouteTablesRequest = DescribeRouteTablesRequest.builder()
                .filters(filter)
                .build();

        try {
            DescribeRouteTablesResponse describeRouteTablesResponse = ec2Client
                    .describeRouteTables(describeRouteTablesRequest);

            List<RouteTable> routeTables = describeRouteTablesResponse.routeTables();

            sharedHandler.verifyIfResponseIsCorrect(routeTables, routeName);

            RouteTable routeTable = routeTables.get(0);

            System.out.println(routeTable);

            return routeTable.routeTableId();
        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

    @Override
    public AssociateRouteTableResponse associateRouteTableToSubnet(String subnetId, String routeTableId) throws Ec2Exception {
        AssociateRouteTableRequest associateRouteTableRequest = AssociateRouteTableRequest.builder()
            .subnetId(subnetId)
            .routeTableId(routeTableId)
            .build();

        try{
            AssociateRouteTableResponse associateRouteTableResponse = ec2Client.associateRouteTable(associateRouteTableRequest);

            System.out.println("successfully associated subnet (%s)".formatted(subnetId));

            return associateRouteTableResponse;
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());

            throw e;
        }
    }

}
