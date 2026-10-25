package com.ruoyi.platform.config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.utils.MockMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Configuration
public class GenerateQRCodeImageConfig {

    @Autowired
    private AliService aliService;

    public String generateQRCodeImage(String text) {
        try {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos); // 将图片写入字节流，格式为PNG
            byte[] imageBytes = baos.toByteArray(); // 转换成字节数组
            MultipartFile multipartFile = new MockMultipartFile("qrCodeFile", text.split("=")[1] + ".png", "image/png", imageBytes); // 创建MultipartFile对象
            return aliService.sliceUploadFile(multipartFile.getOriginalFilename(), multipartFile);
        } catch (Exception e) {
            return null;
        }
    }

}