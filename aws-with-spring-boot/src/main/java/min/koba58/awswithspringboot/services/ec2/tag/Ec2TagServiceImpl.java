package min.koba58.awswithspringboot.services.ec2.tag;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateTagsRequest;
import software.amazon.awssdk.services.ec2.model.CreateTagsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.Tag;

@Service
@RequiredArgsConstructor
public class Ec2TagServiceImpl implements Ec2TagService{
    private final Ec2Client ec2Client;

    // generate tag name
    @Override
    public Optional<CreateTagsResponse> createTag(String resourceId, String tagName) {
        try{
            Tag tag = Tag.builder()
                .key("Name")
                .value(tagName)
                .build();

            CreateTagsRequest tagsRequest = CreateTagsRequest.builder()
                .resources(resourceId)
                .tags(tag)
                .build();
    
        
            CreateTagsResponse createTagsResponse = ec2Client.createTags(tagsRequest);

            System.out.format("Successfully created %s at %s\n", tagName, resourceId);

            return Optional.of(createTagsResponse);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }
    
}
