package min.koba58.awswithspringboot.services.vpc;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import min.koba58.awswithspringboot.SharedTest;

public class VpcServiceTest extends SharedTest{
    Class<VpcServiceImpl> targetClass = VpcServiceImpl.class;

    @Test
    public void createVpcTest(){
        vpcService.createVpc(vpcName);
    }

    @Test
    public void waitUntilVpcAvailableTest() throws Throwable{
        Method targetMethod = targetClass.getDeclaredMethod("waitUntilVpcAvailable", String.class);
        targetMethod.setAccessible(true);

        targetMethod.invoke(vpcService, "");
    }
}
