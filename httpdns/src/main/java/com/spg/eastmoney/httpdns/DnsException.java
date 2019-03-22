package com.spg.eastmoney.httpdns;

import java.io.IOException;

public class DnsException extends IOException {
    public DnsException(String domain, String message) {
        super(domain + ": " + message);
    }
}
