package min.koba58.awswithspringboot.services.ec2.tag;

import java.util.Optional;

import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;

public interface Ec2TagService {
    Optional<CreateTagsResponse> createTag(String resourceId, String tagName);
}
