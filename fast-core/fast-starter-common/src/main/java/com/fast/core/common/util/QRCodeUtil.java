package com.fast.core.common.util;

import cn.hutool.core.io.FileUtil;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.fast.core.common.exception.CustomException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 **/
public class QRCodeUtil {
    /**
     * 解析二维码,此方法解析一个路径的二维码图片
     * path:图片路径
     */
    public static String byPathParsing(String path) {
        String content = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            //这里判断如果识别不了带LOGO的图片，重新添加上一个属性
            try {
                image = ImageIO.read(new File(path));
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
                //设置编码格式
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                //设置优化精度
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                //设置复杂模式开启（我使用这种方式就可以识别微信的二维码了）
                hints.put(DecodeHintType.PURE_BARCODE, Boolean.TYPE);
                Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
                content = result.getText();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (NotFoundException e2) {
                e2.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 通过二维码下载文件
     *
     * @Date: 2022/6/8 21:21
     * @param path: 二维码全路径
     * @param savePath: 文件保存路径
     * @param filename: 文件名可不填(UUID)
     * @param suffix: 文件后缀 (.text  .mp4  ...)
     * @return: void
     **/
    public static void byQrCodeDownloadFile(String path, String savePath, String filename, String suffix,boolean network) {
        if (com.fast.core.common.util.SUtil.isEmpty(suffix)) {
            throw new CustomException("文件后缀为空");
        }
        if (SUtil.isEmpty(savePath)) {
            throw new CustomException("文件保存路径不能为空");
        }
        if(network){
            new Thread(() -> DownLoadFileUtil.downLoadByUrl(path, savePath, filename, suffix)).start();
        }else {
//            DownLoadFileUtil.downLoadByUrl(byPathParsing(path), savePath, filename, suffix);
            new Thread(() -> DownLoadFileUtil.downLoadByUrl(byPathParsing(path), savePath, filename, suffix)).start();
        }
    }

    public static void main(String[] args) {
        String paperFilePath = "D:\\111";
        String savePath = "D:\\111\\222";
        //获取文件夹下所有的二维码
        List<String> strings = FileUtil.listFileNames(paperFilePath);

        strings.forEach(ele -> {
            byQrCodeDownloadFile(paperFilePath + "\\" + ele, savePath, null, ".mp4",false);
//            byQrCodeDownloadFile(ele, savePath, null, ".png",true);
                }
        );
    }
}
