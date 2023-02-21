package min.koba58.awswithspringboot.configs;

import org.junit.jupiter.api.Test;

public class AwsConfigTest {
    @Test
    void testEc2Client() {
        System.out.println(System.getProperty("user.name"));
        String profileName = System.getenv("PROFILE_NAME");

        System.out.println(profileName);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.name"));
        String profileName = System.getenv("PROFILE_NAME");

        System.out.println(profileName);
    }
}
