package min.koba58.awswithspringboot.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubnetDto {
    @NotNull(message = "subnet name must not be null")
    private String subnetName;
    @NotNull(message = "CIDR name must not be null")
    private String cidrBlockForSubnet;
    @NotNull(message = "availability zone name must not be null")
    private String availabilityZoneName;
}
