package com.llg.filewebview;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.llg.filewebview.utils.OpenOfficeUtil;

import java.io.File;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: Lange
 * @Date: 2019/3/19 13:54
 * @Description:
 */
public class PDFDemo {


    public static void main(String[] args) {
        OpenOfficeUtil.officeToPDF("C:\\Users\\Lange\\Desktop\\华银精治部署手册.docx", "C:\\Users\\Lange\\Desktop\\test.html");
    }
}
