package com.owoonan.owoonan.global.test.testapi;

import com.owoonan.owoonan.global.error.exception.ErrorCode;
import com.owoonan.owoonan.global.test.dto.TestDto;
import com.owoonan.owoonan.global.test.error.TestNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "테스트")
public class TestApi {

    @GetMapping("/test/{test}")
    @ApiOperation(value="get 테스트 입니다", notes="get 테스트 설명입니다")
    public String testGet(@PathVariable String test) {
        if (true) throw new TestNotFoundException(ErrorCode.TEST);
        return test;
    }

    @PostMapping("/test")
    @ApiOperation(value="post 테스트 입니다", notes="post 테스트 설명입니다")
    public TestDto testPost(@RequestBody TestDto testDto) {
        return testDto;
    }
}
