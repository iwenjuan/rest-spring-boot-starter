package cn.iwenjuan.sample.controller;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author li1244
 * @date 2023/4/27 9:17
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping("")
    public String test(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("请求参数：{}", JSON.toJSONString(parameterMap));
        return "success";
    }
}
