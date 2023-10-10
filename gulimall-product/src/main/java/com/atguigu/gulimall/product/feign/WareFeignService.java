package com.atguigu.gulimall.product.feign;

import com.atguigu.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: zhangshuaiyin
 * @date: 2021/3/13 10:11
 */
@FeignClient(value = "mall-ware", url = "localhost:8085/gulimall-ware")
public interface WareFeignService {

    @PostMapping(value = "/ware/waresku/hasStock")
    ResultBean getSkuHasStock(@RequestBody List<Long> skuIds);

}
