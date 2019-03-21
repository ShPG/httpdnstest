package httpdnstest.shipeigang.com.httpdnstest.dns;

import java.io.IOException;

public class DnsException extends IOException {
    public DnsException(String domain, String message) {
        super(domain + ": " + message);
    }
}
