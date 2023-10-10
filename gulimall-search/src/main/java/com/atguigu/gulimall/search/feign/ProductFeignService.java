package com.atguigu.gulimall.search.feign;

import com.atguigu.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: zhangshuaiyin
 * @date: 2021/3/11 15:22
 */
@FeignClient(value = "gulimall-product", url = "localhost:8084")
public interface ProductFeignService {

    @GetMapping("/product/attr/info/{attrId}")
    ResultBean attrInfo(@PathVariable("attrId") Long attrId);

}
