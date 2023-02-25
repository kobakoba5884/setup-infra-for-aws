package min.koba58.awswithspringboot.services.ec2.tag;

import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Filter;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

public interface Ec2TagService {
    CreateTagsResponse createNameTag(String resourceId, String tagName) throws Ec2Exception;

    TagSpecification buildNameTagSpecification(String tagName, ResourceType resourceType);

    Filter buildFilterName(String tagName);
}
