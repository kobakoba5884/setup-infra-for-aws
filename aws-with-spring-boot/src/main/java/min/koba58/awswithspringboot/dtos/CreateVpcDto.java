package min.koba58.awswithspringboot.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVpcDto {
    private String vpcName;
    private String sidrBlockForVpc;
}
