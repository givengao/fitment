package com.zxyun.user.controller;

import com.zxyun.user.annotation.Auth;
import com.zxyun.user.base.BaseController;
import com.zxyun.user.base.BaseDto;
import com.zxyun.user.base.Rdto;
import com.zxyun.user.export.factory.XSGExcelHelper;
import com.zxyun.user.util.BResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @GetMapping("/ping")
    @Auth
    public BResponse ping (@RequestBody BaseDto baseDto) {
        return BResponse.ok(baseDto);
    }

    @PostMapping("/register")
    public BResponse register () {
        return BResponse.ok();
    }

    @PostMapping("/login")
    public BResponse login () {
        return BResponse.ok();
    }

    @PostMapping("/changePwd")
    @Auth
    public BResponse changePwd () {
        return BResponse.ok();
    }

    @GetMapping("/export")
    public void export (HttpServletResponse response) {
        List<BaseDto> baseDtos = new ArrayList<>();
        BaseDto baseDto = new BaseDto();
        baseDto.setCompanyId(2L);
        baseDto.setUserId(3L);
        baseDto.setUserName("sasa");
        baseDto.setGroup(5);
        baseDtos.add(baseDto);
        BaseDto baseDto1 = new BaseDto();
        baseDto1.setCompanyId(2L);
        baseDto1.setUserId(3L);
        baseDto1.setUserName("sasa");
        baseDto1.setGroup(5);
        baseDtos.add(baseDto1);

        List<Rdto> baseControllers = new ArrayList<>();
        Rdto rdto = new Rdto();
        rdto.setRname("我是");
        baseControllers.add(rdto);
        Rdto rdto1 = new Rdto();
        rdto1.setRname("我是12");
        baseControllers.add(rdto1);



        XSGExcelHelper.Builder
                .from(baseDtos)
                .append("daada1", e -> e.getUserName())
                .append("daada2", e -> e.getUserName())
                .append("daada3", e -> e.getCompanyId().toString())
                .append("daada1", e -> e.getUserName())
                .append("daada2", e -> e.getUserName())
                .append("daada3", e -> e.getCompanyId().toString())
                .append("daada1", e -> e.getUserName())
                .append("daada2", e -> e.getUserName())
                .append("daada3", e -> e.getCompanyId().toString())
                .append("daada1", e -> e.getUserName())
                .append("daada2", e -> e.getUserName())
                .append("daada3", e -> e.getCompanyId().toString())
                .sheetTitle("w我是")
                .and(baseControllers)
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .sheetTitle("sheet1")
                .and(baseControllers)
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .sheetTitle("sheet3")
                .and(baseControllers)
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .append("daada1", e -> e.getRname())
                .append("daada2", e -> e.getRname())
                .append("daada3", e -> e.getRname())
                .append("daada4", e -> e.getRname())
                .sheetTitle("123")
                .fileName("book1")
                .build()
                .export(response);
    }
}
