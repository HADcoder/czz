package com.diet.controller;

import cn.hutool.core.io.file.FileReader;
import com.diet.core.base.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author LiuYu
 * @date 2018/9/29
 */
@RestController
@RequestMapping(BaseController.API)
public class ApiController extends BaseController {

    @Value("${diet.file.base.path}")
    private String basePath;

    @GetMapping("/downloadFile/{fileName}")
    public void downloadFile(@PathVariable("fileName") String fileName) {
        FileReader fileReader = new FileReader(basePath + File.separator + fileName);
        writeToOutputSteam("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fileName, fileReader.getInputStream());
    }

    private void writeToOutputSteam(String fileType, String fileName, InputStream inputStream) {
        try {
            HttpServletResponse response = getResponse();
            response.setContentType(fileType);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            OutputStream outputStream = response.getOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new DataInputStream(inputStream));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = bufferedInputStream.read(buff)) != -1) {
                //length 代表实际读取的字节数
                bufferedOutputStream.write(buff, 0, length);
            }
            //缓冲区的内容写入到文件
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            bufferedInputStream.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("writeToOutputSteam error.{}, {}, {}", fileType, fileName, e);
        }
    }
}
