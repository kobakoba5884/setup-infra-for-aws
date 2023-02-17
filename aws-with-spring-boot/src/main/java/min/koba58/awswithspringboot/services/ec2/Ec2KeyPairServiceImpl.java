package min.koba58.awswithspringboot.services.ec2;

import static min.koba58.awswithspringboot.configs.Ec2Config.FOLDER_PATH;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.CreateKeyPairResponse;
import software.amazon.awssdk.services.ec2.model.DeleteKeyPairRequest;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsRequest;
import software.amazon.awssdk.services.ec2.model.DescribeKeyPairsResponse;
import software.amazon.awssdk.services.ec2.model.Ec2Exception;
import software.amazon.awssdk.services.ec2.model.KeyPairInfo;

import min.koba58.awswithspringboot.utils.IOHandler;

@Service
@RequiredArgsConstructor
public class Ec2KeyPairServiceImpl implements Ec2KeyPairService{
    private final Ec2Client ec2Client;

    private final IOHandler IOHandler;
    
    // create key pair
    @Override
    public void createEc2KeyPair(String keyPairName){
        try{
            CreateKeyPairRequest createKeyPairRequest = CreateKeyPairRequest.builder()
                .keyName(keyPairName)
                .build();

            CreateKeyPairResponse createKeyPairResponse = ec2Client.createKeyPair(createKeyPairRequest);
            System.out.printf("Successfully created key pair named %s\n", keyPairName);

            String privateKey = createKeyPairResponse.keyMaterial();

            createPemFile(keyPairName, privateKey);

        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    // get key pair list
    @Override
    public Optional<List<KeyPairInfo>> getEc2KeyPairs(){
        
        try{
            DescribeKeyPairsResponse response = ec2Client.describeKeyPairs();
            List<KeyPairInfo> keyPairInfos = response.keyPairs();

            if(keyPairInfos.size() == 0) return Optional.empty();

            keyPairInfos.forEach(keyPair -> {
                System.out.printf("Found key pair with name %s and fingerprint %s\n", keyPair.keyName(), keyPair.keyFingerprint());
            });

            return Optional.of(keyPairInfos);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // get key pair
    @Override
    public Optional<KeyPairInfo> getEc2KeyByKeyName(String keyPairName){
        try{
            DescribeKeyPairsRequest describeKeyPairsRequest = DescribeKeyPairsRequest.builder()
                .keyNames(keyPairName)
                .build();

            DescribeKeyPairsResponse describeKeyPairsResponse = ec2Client.describeKeyPairs(describeKeyPairsRequest);

            KeyPairInfo keyPairInfo = describeKeyPairsResponse.keyPairs().get(0);

            System.out.printf("found keyPair!! (%s)\n", keyPairName);

            return Optional.of(keyPairInfo);
        }catch(Ec2Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            return Optional.empty();
        }
    }

    // delete key pair
    @Override
    public void deleteKeyPair(String keyPairName){
        try{
            DeleteKeyPairRequest deleteKeyPairRequest = DeleteKeyPairRequest.builder()
                .keyName(keyPairName)
                .build();
            
            ec2Client.deleteKeyPair(deleteKeyPairRequest);
            System.out.printf("Successfully deleted key pair named %s\n", keyPairName);

            deletePemFile(keyPairName);
        }catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }

    // all delete key pair
    @Override
    public void allDeleteKeys(){
        Optional<List<KeyPairInfo>> keyPairInfosOptional = getEc2KeyPairs();

        if(keyPairInfosOptional.isEmpty()){
            System.out.println("key pairs's list is empty!!");
            return;
        }

        List<KeyPairInfo> keyPairInfos = keyPairInfosOptional.get();

        keyPairInfos.stream().forEach(keyPair -> deleteKeyPair(keyPair.keyName()));

        System.out.printf("successfully delete all of key pairs!! (total: %d)\n", keyPairInfos.size());
    }

    // create pem file of key pair (private key) 
    private void createPemFile(String keyPairName, String privateKey){
        String fileName = String.format("%s.pem", keyPairName);
        Path outputPath = FOLDER_PATH.resolve(fileName);
        File outputFile = outputPath.toFile();

        try{
            if(FOLDER_PATH.toFile().mkdir() | outputFile.createNewFile()){
                System.out.println(outputPath);
                try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));){
                    bufferedWriter.write(privateKey);
                    System.out.printf("copy complete! to %s\n", outputPath.toString());
                }
            }else{
                System.out.printf("%s is already existed!!\n", outputFile.getName());
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    // delete pem file
    private void deletePemFile(String keyPairName){
        String fileName = String.format("%s.pem", keyPairName);
        Path targetPath = FOLDER_PATH.resolve(fileName);

        if(targetPath.toFile().delete()){
            System.out.printf("Deleted the file: %s\n", fileName);
        }else{
            System.out.println("Failed to delete the file.");
        }

        if(IOHandler.folderIsEmpty(FOLDER_PATH)) {
            FOLDER_PATH.toFile().delete();
        }
    }
}
