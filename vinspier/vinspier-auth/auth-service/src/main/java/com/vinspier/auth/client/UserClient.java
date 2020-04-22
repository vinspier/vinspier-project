package com.vinspier.auth.client;

import com.vinspier.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-service")
public interface UserClient extends UserApi{
}
