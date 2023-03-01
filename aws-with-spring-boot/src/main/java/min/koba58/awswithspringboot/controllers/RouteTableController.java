package min.koba58.awswithspringboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import min.koba58.awswithspringboot.services.vpc.routetable.RouteTableService;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = RouteTableController.BASE_URL)
public class RouteTableController {
    public static final String BASE_URL = "/api/v1/route-table";

    private final RouteTableService routeTableService;

    @PostMapping("")
    public ResponseEntity<String> createRouteTable(@RequestParam String routeTableName, @RequestParam String vpcId){
        try{
            String routeTableId = routeTableService.createRouteTable(routeTableName, vpcId);

            return ResponseEntity.ok("successfully created route table (%s)".formatted(routeTableId));
        }catch(Ec2Exception e){
            return ResponseEntity.badRequest().body(e.awsErrorDetails().errorMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteRouteTable(@RequestParam String routeTableId){
        try{
            routeTableService.deleteRouteTableById(routeTableId);

            return ResponseEntity.ok("successfully deleted route table (%s)".formatted(routeTableId));
        }catch(Ec2Exception e){
            return ResponseEntity.badRequest().body(e.awsErrorDetails().errorMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> associatedRouteTableToSubnet(@RequestParam String subnetId, @RequestParam String routeTableId){
        try{
            routeTableService.associateRouteTableToSubnet(subnetId, routeTableId);

            return ResponseEntity.ok("successfully associated route table (%s) to subnet (%s)".formatted(routeTableId, subnetId));
        }catch(Ec2Exception e){
            return ResponseEntity.badRequest().body(e.awsErrorDetails().errorMessage());
        }
    }
}
