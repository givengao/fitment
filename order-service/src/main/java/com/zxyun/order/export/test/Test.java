package com.zxyun.order.export.test;

import com.zxyun.order.base.BaseDto;
import com.zxyun.order.export.factory.XSGImporterHelper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @des:
 * @Author: given
 * @Date 2019/9/6 20:09
 */
public class Test {

    public static void main (String args[]) {

//        List<BaseDto> baseDtos = new ArrayList<>();
//        BaseDto baseDto = new BaseDto();
//        baseDto.setCompanyId(2L);
//        baseDto.setUserId(3L);
//        baseDto.setUserName("sasa");
//        baseDto.setGroup(5);
//        baseDtos.add(baseDto);
//        BaseDto baseDto1 = new BaseDto();
//        baseDto1.setCompanyId(2L);
//        baseDto1.setUserId(3L);
//        baseDto1.setUserName("sasa");
//        baseDto1.setGroup(5);
//        baseDtos.add(baseDto1);
//
//        List<Rdto> baseControllers = new ArrayList<>();
//        Rdto rdto = new Rdto();
//        rdto.setRname("我是");
//        baseControllers.add(rdto);
//        Rdto rdto1 = new Rdto();
//        rdto1.setRname("我是12");
//        baseControllers.add(rdto1);
//
//
//
//        XSGExcelHelper.Builder
//                .from(baseDtos)
//                .append("daada1", e -> e.getUserName())
//                .append("daada2", e -> e.getUserName())
//                .append("daada3", e -> e.getCompanyId().toString())
//                .append("daada1", e -> e.getUserName())
//                .append("daada2", e -> e.getUserName())
//                .append("daada3", e -> e.getCompanyId().toString())
//                .append("daada1", e -> e.getUserName())
//                .append("daada2", e -> e.getUserName())
//                .append("daada3", e -> e.getCompanyId().toString())
//                .append("daada1", e -> e.getUserName())
//                .append("daada2", e -> e.getUserName())
//                .append("daada3", e -> e.getCompanyId().toString())
//                .sheetTitle("w我是")
//                .and(baseControllers)
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .sheetTitle("sheet1")
//                .and(baseControllers)
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .sheetTitle("sheet3")
//                .and(baseControllers)
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .append("daada1", e -> e.getRname())
//                .append("daada2", e -> e.getRname())
//                .append("daada3", e -> e.getRname())
//                .append("daada4", e -> e.getRname())
//                .sheetTitle("123")
//                .fileName("book1")
//                .build()
//                .export();

        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
        try {
            InputStream in = file.getInputStream();
            String fileName = file.getOriginalFilename();
            List<BaseDto> list = new ArrayList<>();
            List<BaseDto> list1 = XSGImporterHelper
                    .builder().from(in).fileName(fileName).to(BaseDto.class)
                    .append(0, (t, s) -> t.setCompanyId(((Long)s)))
                    .append(1, (t, s) -> {})
                    .end().build().importer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
