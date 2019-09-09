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

        List<Rdto> rdtos = new ArrayList<>();
        Rdto rdto = new Rdto();
        rdto.setRname("我是");
        rdtos.add(rdto);
        Rdto rdto1 = new Rdto();
        rdto1.setRname("我是12");
        rdtos.add(rdto1);


        XSGExcelHelper.Builder.from(baseDtos)
                .append("国家", e -> e.getUserName())
                .append("地区", e -> e.getUserName())
                .append("性别", e -> e.getCompanyId().toString())
                .append("年龄", e -> e.getUserName())
                .append("地址", e -> e.getUserName())
                .append("工资", e -> e.getCompanyId().toString())
                .append("公司", e -> e.getUserName())
                .append("人数", e -> e.getUserName())
                .append("性格", e -> e.getCompanyId().toString())
                .append("兴趣", e -> e.getUserName())
                .append("心上", e -> e.getUserName())
                .append("大王", e -> e.getCompanyId().toString())
                .sheetTitle("个人资料1")
                .and(rdtos)
                .append("名称", e -> e.getRname())
                .append("地区", e -> e.getRname())
                .append("性别", e -> e.getRname().toString())
                .append("年龄", e -> e.getRname())
                .append("地址", e -> e.getRname())
                .append("工资", e -> e.getRname().toString())
                .append("公司", e -> e.getRname())
                .append("人数", e -> e.getRname())
                .append("性格", e -> e.getRname().toString())
                .append("兴趣", e -> e.getRname())
                .append("心上", e -> e.getRname())
                .append("大王", e -> e.getRname().toString())
                .sheetTitle("文件信息2")
                .and(rdtos)
                .append("名称", e -> e.getRname())
                .append("地区", e -> e.getRname())
                .append("性别", e -> e.getRname().toString())
                .append("年龄", e -> e.getRname())
                .append("地址", e -> e.getRname())
                .append("工资", e -> e.getRname().toString())
                .append("公司", e -> e.getRname())
                .append("人数", e -> e.getRname())
                .append("性格", e -> e.getRname().toString())
                .append("兴趣", e -> e.getRname())
                .append("心上", e -> e.getRname())
                .append("大王", e -> e.getRname().toString())
                .sheetTitle("文件信息3")
                .and(rdtos)
                .append("名称", e -> e.getRname())
                .append("地区", e -> e.getRname())
                .append("性别", e -> e.getRname().toString())
                .append("年龄", e -> e.getRname())
                .append("地址", e -> e.getRname())
                .append("工资", e -> e.getRname().toString())
                .append("公司", e -> e.getRname())
                .append("人数", e -> e.getRname())
                .append("性格", e -> e.getRname().toString())
                .append("兴趣", e -> e.getRname())
                .append("心上", e -> e.getRname())
                .append("大王", e -> e.getRname().toString())
                .sheetTitle("文件信息4")
                .fileName("大王总资料.xlsx")
                .build()
                .export();

        List<? extends Integer> integers = new ArrayList<>();
        Integer a = integers.get(0);
    }
}
