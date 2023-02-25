package min.koba58.awswithspringboot.configs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.ec2.Ec2Client;

public class AwsConfigTest {
    AwsConfig target = new AwsConfig();

    @Test
    void testEc2Client() {
        Ec2Client result = target.ec2Client();

        assertNotNull(result);
    }
}
