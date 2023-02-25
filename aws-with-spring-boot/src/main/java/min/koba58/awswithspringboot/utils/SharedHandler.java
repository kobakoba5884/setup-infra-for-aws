package min.koba58.awswithspringboot.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Component
public class SharedHandler {
    public void verifyIfResponseIsCorrect(List<?> resources, String resourceName) throws Ec2Exception{
        if(resources.isEmpty()){
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                .errorMessage("not found resource (%s)".formatted(resourceName)).build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }else if (resources.size() > 1) {
            AwsErrorDetails awsErrorDetails = AwsErrorDetails.builder()
                    .errorMessage("It don't know which one %s is because there is more than one."
                            .formatted(resourceName))
                    .build();

            throw Ec2Exception.builder().awsErrorDetails(awsErrorDetails).build();
        }
    }
}
