package min.koba58.awswithspringboot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.iam.IamClient;

import static min.koba58.awswithspringboot.configs.GlobalConfig.MY_REGION;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class AwsConfig {
    public static final String EC2_FOLDER_PATH = "workspace/private-key-for-ec2";
    public static final Path BASE_FOLDER_PATH = Paths.get(System.getProperty("user.home"));
    public static final Path FOLDER_PATH = BASE_FOLDER_PATH.resolve(EC2_FOLDER_PATH);

    @Bean
    public Ec2Client ec2Client() {
        return Ec2Client.builder()
                .region(MY_REGION)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    @Bean
    public IamClient iamClient() {
        return IamClient.builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

}
