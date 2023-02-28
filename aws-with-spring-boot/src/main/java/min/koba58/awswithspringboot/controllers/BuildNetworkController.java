package min.koba58.awswithspringboot.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.dtos.CreateMultiAZDto;
import min.koba58.awswithspringboot.services.network.BuildNetworkService;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = BuildNetworkController.BASE_URL)
public class BuildNetworkController {
    public static final String BASE_URL = "/api/v1/vpc";

    private final BuildNetworkService buildNetworkService;

    @PostMapping("/multiAZ")
    public ResponseEntity<String> createMultiAZ(@Valid @RequestBody CreateMultiAZDto dto){
        try{
            buildNetworkService.createVpcForBasicMultiAZ(dto);

            return ResponseEntity.ok("multi az vpc created successfully.");
        } catch (Ec2Exception e) {
            return ResponseEntity.badRequest().body(e.awsErrorDetails().errorMessage());
        }
    }
}
