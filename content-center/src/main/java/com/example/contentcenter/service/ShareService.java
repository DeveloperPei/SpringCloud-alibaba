package com.example.contentcenter.service;

import com.example.contentcenter.dao.content.ShareMapper;
import com.example.contentcenter.domain.dto.ShareDTO;
import com.example.contentcenter.domain.dto.UserDTO;
import com.example.contentcenter.domain.entity.content.Share;
import com.example.contentcenter.feignClient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShareService {
    @Resource
    private ShareMapper shareMapper;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    public ShareDTO findById(Integer id){
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
    /*    String targetUrl = instances.stream()
                .map(instance -> instance.getUri().toString() + "user/{id}")
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("当前没有实例"));*/
        List<String> targetUrls = instances.stream()
                .map(instance -> instance.getUri().toString() + "user/{id}")
                .collect(Collectors.toList());
        int i = ThreadLocalRandom.current().nextInt(targetUrls.size());
        String targetUrl = targetUrls.get(i);

        UserDTO userDTO = restTemplate.getForObject(targetUrl, UserDTO.class,userId);
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return  shareDTO;
    }

    public ShareDTO findById2(Integer id){
        Share share = shareMapper.selectByPrimaryKey(id);
        UserDTO userDTO = userCenterFeignClient.findById(id);
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return  shareDTO;
    }
}
