package min.koba58.awswithspringboot.services.ec2.keyPair;

import java.util.List;
import java.util.Optional;

import software.amazon.awssdk.services.ec2.model.KeyPairInfo;

public interface Ec2KeyPairService {
    void createEc2KeyPair(String keyPairName);

    Optional<List<KeyPairInfo>> getEc2KeyPairs();

    Optional<KeyPairInfo> getEc2KeyByKeyName(String keyPairName);

    void deleteKeyPair(String keyPairName);

    void allDeleteKeys();


}
