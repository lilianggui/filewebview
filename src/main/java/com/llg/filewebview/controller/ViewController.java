package com.llg.filewebview.controller;

import com.llg.filewebview.utils.OpenOfficeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther: Lange
 * @Date: 2019/3/19 14:43
 * @Description:
 */
@Controller
public class ViewController {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String path = request.getServletContext().getRealPath("/");
        String fileName = file.getOriginalFilename();
        String filePath = path + "/temp/";
        File ff = new File(filePath);
        if(!ff.exists()){
            ff.mkdirs();
        }
        File dest = new File(ff, fileName);
        try {
            file.transferTo(dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败！";
    }

    @GetMapping("/viewWord")
    public void viewWord(HttpServletResponse response, String filePath, HttpServletRequest request)  throws Exception{
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        String path = request.getServletContext().getRealPath("/changeFile");
        File changeFile = new File(path);
        if(!changeFile.exists()){
            changeFile.mkdirs();
        }
        String destFileName =  UUID.randomUUID().toString() + ".html";
        String destFilePath = path + "/" + destFileName;
        OpenOfficeUtil.officeToPDF(filePath, destFilePath);

        BufferedInputStream br = new BufferedInputStream(new FileInputStream(destFilePath));
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        URL u = new URL("file:///" + destFilePath);
        response.setContentType(u.openConnection().getContentType());
        response.setHeader("Content-Disposition", "inline; filename=" + destFileName);

//        response.setContentType("application/x-msdownload");
//        response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());

        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }
}
