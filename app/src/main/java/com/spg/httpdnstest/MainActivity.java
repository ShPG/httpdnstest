package com.spg.httpdnstest;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spg.eastmoney.httpdns.DnsManager;
import com.spg.eastmoney.httpdns.IResolver;
import com.spg.eastmoney.httpdns.NetworkInfo;
import com.spg.eastmoney.httpdns.local.Resolver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import okhttp3.Dns;

import com.spg.httpdnstest.R;

public class MainActivity extends AppCompatActivity {

    private TextView ip;
    private String address="http://10.228.129.134:8080/aiohttpdns/d?host=www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip=findViewById(R.id.ip);

        ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebviewActivity.class));
            }
        });


        new Thread(){
            public void run(){

                try {

                    Uri uri = Uri.parse(address);

                    IResolver[] resolvers = new IResolver[1];
                    resolvers[0] = new Resolver(InetAddress.getByName(uri.getHost()));
                    DnsManager dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
                    String[] ips = dnsManager.query(uri.getQueryParameter("host"));  //获取HttpDNS解析结果


                    List<InetAddress> result;

                    if (ips == null || ips.length == 0) {
                        result=Dns.SYSTEM.lookup("www.baidu.com");
                    }else
                    {
                        result = new ArrayList<>();

                        for (String ip : ips) {  //将ip地址数组转换成所需要的对象列表
                            result.addAll(Arrays.asList(InetAddress.getAllByName(ip)));
                        }
                    }




                    Message m=Message.obtain();
                    m.what=1;
                    m.obj=result.get(0).getHostName();
                    handler.sendMessage(m);


                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();

//        http://10.228.129.134:8080/aiohttpdns/d?host=www.baidu.com



    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                    ip.setText(msg.obj.toString());
            }

        }
    };




}
