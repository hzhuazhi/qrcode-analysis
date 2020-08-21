package com.qrcode.analysis.master.core.common.utils;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Description 微信二维码解析工具
 * @Author yoko
 * @Date 2020/8/20 15:14
 * @Version 1.0
 */
public class QrCodeUtil {

    public static String decode(String qrcodePicfilePath) {
        System.out.println("开始解析二维码！！");
        /* 读取二维码图像数据 */
        File imageFile = new File(qrcodePicfilePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("读取二维码图片失败： " + e.getMessage());
            return null;
        }
        /* 解析二维码 */
        QRCodeDecoder decoder = new QRCodeDecoder();
        String decodedData = new String(
                decoder.decode(new QrCodeUtil.J2SEImageGucas(image)));
        System.out.println("解析内容如下：" + decodedData);
        return decodedData;
    }

    public static class J2SEImageGucas implements QRCodeImage {
        BufferedImage image;

        public J2SEImageGucas(BufferedImage image) {
            this.image = image;
        }

        public int getWidth() {
            return image.getWidth();
        }

        public int getHeight() {
            return image.getHeight();
        }

        public int getPixel(int x, int y) {
            return image.getRGB(x, y);
        }
    }


    public static  void main(String [] args){
//        decode("D:/test/427发小8.jpg");
//        String str1 ="C:\\Users\\kk\\Documents\\WeChat Files\\yunbatiao\\FileStorage\\Image\\2020-08\\91fe0f717a5d5428a65d4d8d0d8efa5e.jpg";
        String str1 ="C:/Users/duanf/Documents/WeChat Files/wxid_kqx4n8p9tseu12/FileStorage/Image/2020-08/047e3964b02f32768266be76566f14f7.jpg";
        decode(str1);

//            String sb1 = "C:/Users/kk/Documents/WeChat Files/yunbatiao/FileStorage/Image/2020-08/91fe0f717a5d5428a65d4d8d0d8efa5e.jpg";
        String sb1 = "C:\\Users\\kk\\Documents\\WeChat Files\\yunbatiao\\FileStorage\\Image\\2020-08\\91fe0f717a5d5428a65d4d8d0d8efa5e.jpg";
        sb1 = sb1.replaceAll("\\\\", "/");// 将双反斜杠替换成单正斜杠
        System.out.println("sb1:" + sb1);
        String sb2 = sb1.substring(sb1.lastIndexOf("/") + 1, sb1.length());
        System.out.println("sb2:" + sb2);

    }
}
