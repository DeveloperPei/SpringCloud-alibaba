# SpringCloud-alibaba 学习项目
项目分为两部分 , 内容中心和用户中心
## 服务治理使用nacos
1 无需注解,直接引入依赖后使用  
2 领域模型:NameSpace--Group--Service--Cluster--Instance  
3 跨NameSpace的服务不能相互调用

## mybatis使用的是通用mapper
1 自带了简单的增删改查  
2 需要在启动类加入注解  -- 通用mapper扫描接口  
3 能自动生成代码,在配置文件夹下面创建文件,本项目是创建了  
&emsp;&emsp;/generator/config.properties  
&emsp;&emsp;/generator/generatorConfig.xml  
4 在maven面板里的plugins里找到mybatis-generator点击即可代码生成  
5 pom.xml添加插件
```
   <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.3.6</version>
        <configuration>
            <configurationFile>
                ${basedir}/src/main/resources/generator/generatorConfig.xml
            </configurationFile>
            <overwrite>true</overwrite>
            <verbose>true</verbose>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.17</version>
            </dependency>
            <dependency>
                <groupId>tk.mybatis</groupId>
                <artifactId>mapper</artifactId>
                <version>4.1.5</version>
            </dependency>
        </dependencies>
    </plugin>
```
## mybatis使用的是通用mapper
1 RestTemplate+ Ribbon 组合,与常规一样  
2 Ribbon配置可以代码和文件配置,取其一种  
3 配置注意是全局还是针对某个服务的配置  
4 默认轮询负载均衡,可以继承AbstractLoadBalancerRule重写负载均衡方法
