package com.example.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

@Configuration
@RibbonClient(value = "user-center", configuration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {

}
