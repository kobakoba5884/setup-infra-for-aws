package min.koba58.awswithspringboot.services.vpc.az;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.services.vpc.VpcSharedTest;

public class AvailabilityZoneServiceImplTest extends VpcSharedTest{
    @Test
    void testGetAvailabilityZoneNamesByRegion() {
        List<String> result = availabilityZoneService.getAvailabilityZoneNames();

        result.stream().forEach(System.out::println);

        assertFalse(result.isEmpty());
    }
}
