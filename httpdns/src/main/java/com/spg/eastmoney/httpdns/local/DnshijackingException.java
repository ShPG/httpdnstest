package com.spg.eastmoney.httpdns.local;


import com.spg.eastmoney.httpdns.DnsException;

public class DnshijackingException extends DnsException {
    public DnshijackingException(String domain, String server) {
        super(domain, "has hijacked by " + server);
    }

    public DnshijackingException(String domain, String server, int ttl) {
        super(domain, "has hijacked by " + server + " ttl " + ttl);
    }
}
