package httpdnstest.shipeigang.com.httpdnstest;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import httpdnstest.shipeigang.com.httpdnstest.dns.DnsManager;
import httpdnstest.shipeigang.com.httpdnstest.dns.IResolver;
import httpdnstest.shipeigang.com.httpdnstest.dns.NetworkInfo;
import httpdnstest.shipeigang.com.httpdnstest.dns.local.Resolver;
import okhttp3.Dns;

/**
 * Created by sipg on 19/3/21.
 */

public class HTTPDNS implements Dns {

    private DnsManager dnsManager;

    public HTTPDNS() {
        IResolver[] resolvers = new IResolver[1];
        try {
            resolvers[0] = new Resolver(InetAddress.getByName("119.29.29.29"));
            dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (dnsManager == null)  //当构造失败时使用默认解析方式
            return Dns.SYSTEM.lookup(hostname);

        try {
            String[] ips = dnsManager.query(hostname);  //获取HttpDNS解析结果
            if (ips == null || ips.length == 0) {
                return Dns.SYSTEM.lookup(hostname);
            }

            List<InetAddress> result = new ArrayList<>();
            for (String ip : ips) {  //将ip地址数组转换成所需要的对象列表
                result.addAll(Arrays.asList(InetAddress.getAllByName(ip)));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //当有异常发生时，使用默认解析
        return Dns.SYSTEM.lookup(hostname);
    }
}
