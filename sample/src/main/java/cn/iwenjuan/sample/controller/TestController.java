package cn.iwenjuan.sample.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public String get(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("get请求参数：{}", JSON.toJSONString(parameterMap));
        return JSONObject.toJSONString(parameterMap);
    }

    @PostMapping("")
    public JSONObject post(@RequestBody JSONObject object) {
        log.info("post请求参数：{}", object.toJSONString());
        return object;
    }

    @PutMapping("")
    public JSONObject put(@RequestBody JSONObject object) {
        log.info("put请求参数：{}", object.toJSONString());
        return object;
    }

    @DeleteMapping("")
    public String delete(@RequestBody JSONObject object) {
        log.info("delete请求参数：{}", object.toJSONString());
        return object.toJSONString();
    }
}
