package min.koba58.awswithspringboot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.profiles.Profile;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.budgets.BudgetsClient;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.iam.IamClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Configuration
public class AwsConfig {
    public static final String EC2_FOLDER_PATH = "workspace/private-key-for-ec2";
    public static final Path BASE_FOLDER_PATH = Paths.get(System.getProperty("user.home"));
    public static final Path FOLDER_PATH = BASE_FOLDER_PATH.resolve(EC2_FOLDER_PATH);
    private String profileName = getProfileName();
    public Region DEFAULT_REGION = Region.of(getDefaultRegion());
    private ProfileCredentialsProvider profileCredentialsProvider = createProfileCredentialsProvider();

    @Bean
    public Ec2Client ec2Client() {
        return Ec2Client.builder()
                .region(DEFAULT_REGION)
                .credentialsProvider(profileCredentialsProvider)
                .build();
    }

    @Bean
    public IamClient iamClient() {
        return IamClient.builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(profileCredentialsProvider)
                .build();
    }

    @Bean
    public BudgetsClient budgetsClient() {
        return BudgetsClient.builder()
                .region(Region.AWS_GLOBAL)
                .credentialsProvider(profileCredentialsProvider)
                .build();
    }

    
    private Profile getProfile(){
        Path pathToHomeDir = Paths.get(System.getenv("HOME"));
        File file = pathToHomeDir.resolve(".aws/config").toFile();
        Profile profile = null;

        try (InputStream content = new FileInputStream(file)) {
            ProfileFile profileFile = ProfileFile.builder()
                    .content(content)
                    .type(ProfileFile.Type.CONFIGURATION)
                    .build();

            Optional<Profile> profileOption = profileFile.profile(profileName);

            if (profileOption.isEmpty()) {
                String message = "not found profile (%s)".formatted(profileName);

                throw new IOException(message);
            }

            profile = profileOption.get();
        } catch (IOException e) {
            System.out.println(e.getMessage());

            System.exit(1);
        }

        return profile;
    }

    private ProfileCredentialsProvider createProfileCredentialsProvider() {
        return ProfileCredentialsProvider.builder()
                .profileName(profileName)
                .build();
    }

    private String getProfileName() {
        String profileName = System.getenv("PROFILE_NAME");

        return Objects.isNull(profileName) ? "default" : profileName;
    }

    private String getDefaultRegion(){
        Profile profile = getProfile();

        Optional<String> defaultRegion = profile.property("region");

        if (defaultRegion.isEmpty()) {
            System.out.println("not found region");

            System.exit(1);
        }

        String region = defaultRegion.get();

        System.out.println("default region is %s".formatted(region));

        return region;
    }
}
