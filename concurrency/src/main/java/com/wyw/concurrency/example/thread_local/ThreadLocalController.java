package com.wyw.concurrency.example.thread_local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 验证ThreadLocal
 */
@Controller
@RequestMapping("/threadLocal")
public class ThreadLocalController {

    @RequestMapping("test")
    @ResponseBody
    public Long test(){
        return RequestHolder.getId();
    }
}
