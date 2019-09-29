package com.example.contentcenter.configuration;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosDiscoveryProperties;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NacosSameClusterWeightedRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        try {
            //拿到配置文件中的集群名称
            String clusterName = discoveryProperties.getClusterName();
            //想要请求的服务名称
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            String name = loadBalancer.getName();
            // 服务发现的相关api
            NamingService namingService = discoveryProperties.namingServiceInstance();
            //找到指定服务的所有实例 A
            List<Instance> instances = namingService.selectInstances(name, true);
            //过滤出相同集群的所有实例 B
            List<Instance> sameClusterInstance = instances.stream().filter(instance -> instance.getClusterName().equals(clusterName)).collect(Collectors.toList());
            //如果B是空就用A
            List<Instance> chosenInstances=null;
            if(CollectionUtils.isEmpty(sameClusterInstance)){
                chosenInstances=instances;
                log.info("发生了跨集群调用,name={} ,clusterName ={},instance ={}",name,clusterName,instances);
            }else {
                chosenInstances=sameClusterInstance;
            }
            //基于权重的负载均衡算法,返回一个实例
            Instance hostByRandomWeight2 = MyBalance.getHostByRandomWeight2(chosenInstances);
            log.info("选择的实例是 ip:{}",hostByRandomWeight2.getIp());
            return new NacosServer(hostByRandomWeight2);
        } catch (NacosException e) {
           log.error("发生异常",e);
            return null;
        }
    }
}

/**
 * nacos 源码的根据权重负载均衡算法
 */
class MyBalance extends Balancer{
    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}
