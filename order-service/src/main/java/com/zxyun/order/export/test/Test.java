package com.zxyun.order.export.test;

import com.zxyun.order.base.BaseDto;
import com.zxyun.order.base.Rdto;
import com.zxyun.order.export.factory.XSGExcelHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/6 20:09
 */
public class Test {

    public static void main (String args[]) {

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
                .export();

    }
}
