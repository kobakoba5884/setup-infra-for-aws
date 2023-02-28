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
public class DeleteMultiAZDto {
    @NotNull(message = "VPC id must not be null")
    private String vpcId;
    @NotNull(message = "Subnet list must not be null")
    @Size(min = 1, message = "SubnetId list must contain 1 elements")
    private List<String> subnetIdList;
    @NotNull(message = "internet gateway id must not be null")
    private String internetGatewayId;
}
