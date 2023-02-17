package min.koba58.awswithspringboot.utils;

import org.springframework.stereotype.Component;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

@Component
public class IpAddressHandler {
    // get global ip address
    public String getMyGlobalIpAddress(){
        IPinfo ipInfo = new IPinfo.Builder().build();
        String result = "0.0.0.0/0";

        try {
            IPResponse response = ipInfo.lookupIP("");

            result = String.format("%s/32", response.getIp());

            System.out.println("successfully get your global ip address!!");
        } catch (RateLimitedException e) {
            System.err.println(e.getMessage());
            return result;
        }

        return result;
    }
}
