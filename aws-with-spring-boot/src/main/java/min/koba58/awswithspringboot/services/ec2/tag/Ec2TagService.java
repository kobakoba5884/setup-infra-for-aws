package min.koba58.awswithspringboot.services.ec2.tag;

import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;

public interface Ec2TagService {
    CreateTagsResponse createTag(String resourceId, String tagName) throws Ec2Exception;
}
