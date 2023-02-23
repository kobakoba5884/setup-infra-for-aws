package min.koba58.awswithspringboot.services.day1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Day1ServiceImplTest {
    @Autowired
    private Day1Service day1Service;

    String userName = "day1-user";
    String groupName = "day1-group";
    String policyName = "AdministratorAccess";

    @Test
    void testCreateUserForDay1() {

    }

    @Test
    void testDeleteUserAndGroupForDay1_normal() {
        assertDoesNotThrow(() -> {
            day1Service.deleteUserAndGroupForDay1(userName, groupName);
        });
    }
}
