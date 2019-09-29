# SpringCloud-alibaba 学习项目
项目分为两部分 , 内容中心和用户中心
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
