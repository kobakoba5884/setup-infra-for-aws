package min.koba58.awswithspringboot.services.vpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateVpcRequest;
import software.amazon.awssdk.services.ec2.model.CreateVpcResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import min.koba58.awswithspringboot.SharedTest;
import min.koba58.awswithspringboot.services.ec2.Ec2TagService;

import static org.mockito.Mockito.*;

public class VpcServiceImplTest extends SharedTest {
    @MockBean
    private Ec2Client ec2Client;

    @MockBean
    private Ec2TagService tagService;

    CreateVpcRequest createVpcRequest = CreateVpcRequest.builder()
            .cidrBlock("10.0.0.0/16").build();

    @Test
    @Order(1)
    public void createVpc_shouldReturnVpcId() {
        String vpcId = "vpc-12345";

        CreateVpcResponse response = CreateVpcResponse.builder()
                .vpc(vpc -> vpc.vpcId(vpcId))
                .build();

        when(ec2Client.createVpc(createVpcRequest))
                .thenReturn(response);

        String result = vpcService.createVpc(vpcName);

        assertEquals(vpcId, result, "Not what vpc id expected (%s)".formatted(result));
    }

    @Test
    @Order(2)
    public void createVpc_ShouldReturnEmptyString_WhenEc2ExceptionOccurs() {
        AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                .errorMessage("VpcLimitExceeded").build();

        AwsServiceException exception = Ec2Exception.builder()
                .awsErrorDetails(awsErrorDetails)
                .build();

        when(ec2Client.createVpc(createVpcRequest)).thenThrow(exception);

        String result = vpcService.createVpc(vpcName);

        assertTrue(result.isEmpty());
    }
}
