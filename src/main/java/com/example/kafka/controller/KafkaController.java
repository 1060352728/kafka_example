package com.example.kafka.controller;

import com.example.kafka.entity.FileEntity;
import com.example.kafka.producer.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @Auther: likui
 * @Date: 2019/9/24 21:35
 * @Description:
 */
@Slf4j
@RestController
public class KafkaController {

    @Autowired
    private KafkaSender<FileEntity> kafkaSender;

    @GetMapping("/test")
    public String testCreatMsg() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setTaskId("12");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("E:\\datafile\\test.csv"));
            StringBuilder sb = new StringBuilder();
            String str;
            while((str = br.readLine()) != null) {//逐行读取
                sb.append(str);//加在StringBuffer尾
                sb.append("\r\n");//行尾 加换行符
            }
            fileEntity.setFileContent(sb.toString());
            kafkaSender.send(fileEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return "SUCCESS";
    }
}
