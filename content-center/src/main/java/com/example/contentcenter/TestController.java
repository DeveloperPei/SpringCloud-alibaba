package com.example.contentcenter;

import com.example.contentcenter.dao.content.ShareMapper;
import com.example.contentcenter.domain.entity.content.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class TestController {
    @Resource
    private ShareMapper shareMapper;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("test")
    public List<Share> test(){
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxxx");
        share.setCover("xxx");
        share.setAuthor("训");
        share.setBuyCount(1);
        shareMapper.insertSelective(share);
        List<Share> shares = shareMapper.selectAll();
        return shares;
    }
    @GetMapping("test2")
    public List<ServiceInstance> getInstance(){
        return  discoveryClient.getInstances("user-center");
    }

}
