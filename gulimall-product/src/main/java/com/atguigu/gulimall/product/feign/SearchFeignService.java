package com.atguigu.gulimall.product.feign;


import com.atguigu.common.to.es.SkuEsModel;
import com.atguigu.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: zhangshuaiyin
 * @date: 2021/3/13 10:11
 */
@FeignClient(value = "mall-search", url = "localhost:8087/gulimall-search")
public interface SearchFeignService {

    @PostMapping(value = "/search/save/product")
    ResultBean productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
