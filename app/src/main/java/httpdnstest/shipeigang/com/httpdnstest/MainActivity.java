package httpdnstest.shipeigang.com.httpdnstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import httpdnstest.shipeigang.com.httpdnstest.dns.DnsManager;
import httpdnstest.shipeigang.com.httpdnstest.dns.IResolver;
import httpdnstest.shipeigang.com.httpdnstest.dns.NetworkInfo;
import httpdnstest.shipeigang.com.httpdnstest.dns.local.Resolver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread(){
            public void run(){
                IResolver[] resolvers = new IResolver[1];
                try {
                    resolvers[0] = new Resolver(InetAddress.getByName("10.228.129.134"));
                    DnsManager dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
                    String[] ips = dnsManager.query("www.baidu.com");  //获取HttpDNS解析结果


                    List<InetAddress> result;

                    if (ips == null || ips.length == 0) {
                        result=Dns.SYSTEM.lookup("www.baidu.com");
                    }

                    result = new ArrayList<>();


                    for (String ip : ips) {  //将ip地址数组转换成所需要的对象列表
                        result.addAll(Arrays.asList(InetAddress.getAllByName(ip)));
                    }


                    for (int i=0;i<result.size();i++)
                    {
                        InetAddress add=result.get(i);
                        String aa=add.getCanonicalHostName();
                        String bb=add.getHostAddress();
                        String cc=add.getHostName();
                        String dd=add.getCanonicalHostName();
                    }


                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e)
                {

                }
            }
        }.start();

//        http://10.228.129.134:8080/aiohttpdns/d?host=www.baidu.com



    }




}
