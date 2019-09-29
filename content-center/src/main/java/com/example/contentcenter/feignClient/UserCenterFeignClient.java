package com.example.contentcenter.feignClient;

import com.example.contentcenter.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-center")
public interface UserCenterFeignClient {

    @GetMapping("/user/{id}")
    UserDTO findById(@PathVariable("id") Integer id);
}
