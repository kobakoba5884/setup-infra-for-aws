package min.koba58.awswithspringboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.day1.Day1Service;
import software.amazon.awssdk.services.iam.model.IamException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = Day1Controller.BASE_URL)
public class Day1Controller {
    public static final String BASE_URL = "/api/v1/day1";

    private final Day1Service day1Service;

    @PostMapping("")
    public ResponseEntity<Object> createUserForDay1(@RequestParam String userName, @RequestParam String groupName,
            @RequestParam String policyName) {

        try {
            day1Service.createUserForDay1(userName, groupName, policyName);

            return ResponseEntity.ok(String.format("User %s with group %s and policy %s created successfully.",
                    userName, groupName, policyName));
        } catch (IamException e) {
            return ResponseEntity.badRequest().body(e.awsErrorDetails().errorMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteUserAndGroupForDay1(@RequestParam String userName, @RequestParam String groupName){
        try {
            day1Service.deleteUserAndGroupForDay1(userName, groupName);

            return ResponseEntity.ok(String.format("User %s with group %s deleted successfully.",
                    userName, groupName));
        } catch (IamException e) {
            return ResponseEntity.badRequest().body(e.awsErrorDetails().errorMessage());
        }
    }

}
