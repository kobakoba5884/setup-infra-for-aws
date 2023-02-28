package min.koba58.awswithspringboot.dtos;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMultiAZDto {
    @NotNull(message = "VPC name must not be null")
    private String vpcName;
    @NotNull(message = "CIDR block must not be null")
    private String sidrBlockForVpc;
    @NotNull(message = "Subnet list must not be null")
    @Size(min = 1, message = "Subnet list must contain 1 elements")
    private List<CreateSubnetDto> subnetList;
    @NotNull(message = "internet gateway name must not be null")
    private String internetGatewayName;
}
