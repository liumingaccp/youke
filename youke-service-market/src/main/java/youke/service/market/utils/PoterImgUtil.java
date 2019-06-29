package youke.service.market.utils;

import com.jd.open.api.sdk.domain.ware.WareReadService.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PoterImgUtil {

    private static int imageWidth = 750;  //图片的宽度
    private static int imageHeight = 1260; //图片的高度
    private static final String IMAGE_PREFIX = "http://img14.360buyimg.com/n7/";

    public static void graphicsGeneration(String name, InputStream mainImgurlInput, InputStream temInput, InputStream codeContentInput, File outFile) {
        BufferedImage bufferedImage = getBufferedImage(name, mainImgurlInput, temInput, codeContentInput);
        createImage(bufferedImage, outFile);
    }

    public static BufferedImage getBufferedImage(String name, InputStream mainImgurlInput, InputStream temInput, InputStream codeContentInput) {
        BufferedImage image = null;

        int H_mainPic = 800;            //主图高度
        int H_range_pic_str = 20;       //图片与文字的间距高度
        int H_range_str_str = 15;         //字符与字符的间距高度
        int H__code = 260;              //二维码高度行业宽度
        int W_range_pic_pic = 70;       //图片与图片的间距宽度
        int str_range = 20;             //文字旁白
        int font_size = 40;             //字符大小
        int title_hight = 0;            //备用参数

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        //***********************插入中间广告图
        Graphics mainPic = image.getGraphics();
        BufferedImage bimg = null;
        try {
            bimg = javax.imageio.ImageIO.read(mainImgurlInput);
        } catch (Exception e) {
        }

        if (bimg != null) {
            mainPic.drawImage(bimg, 0, 0, imageWidth, H_mainPic, null);
            mainPic.dispose();
        }

        //***********************设置中间的标题
        //设置字体
        Graphics2D tip = image.createGraphics();
        tip.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tip.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font tipFont = new Font("Microsoft YaHei", Font.PLAIN, 40);
        tip.setFont(tipFont);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.black);
        if (name != null && !name.equals("") && name.length() > 18) {                 //如果成两页
            tip.drawString(name.substring(0, 18), str_range, H_mainPic + font_size + H_range_pic_str);
            if (name.length() > 36) {
                tip.drawString(name.substring(18, 36) + "....", str_range, H_mainPic + font_size + H_range_pic_str + font_size + H_range_str_str);
            } else {
                tip.drawString(name.substring(18), str_range, H_mainPic + font_size + H_range_pic_str + font_size + H_range_str_str);
            }
            title_hight = H_mainPic + font_size + H_range_pic_str + font_size + H_range_str_str;
        } else {
            tip.drawString(name, str_range, H_mainPic + font_size + H_range_pic_str);
            title_hight = H_mainPic + font_size + H_range_pic_str;
        }

        //***********************二维码,和右边的提示图片
        Graphics2D mainPic2 = image.createGraphics();
        mainPic2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mainPic2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        BufferedImage bimg2 = null;
        BufferedImage bimg3 = null;
        try {
            bimg2 = javax.imageio.ImageIO.read(codeContentInput);
            bimg3 = javax.imageio.ImageIO.read(temInput);
        } catch (Exception e) {
        }

        if (bimg2 != null) {
            mainPic2.drawImage(bimg2, W_range_pic_pic, title_hight + H_range_pic_str * 2, H__code, H__code, null);
        }
        if (bimg3 != null) {
            mainPic2.drawImage(bimg3, W_range_pic_pic * 2 + H__code, title_hight + H_range_pic_str * 2, H__code, H__code, null);
        }
        mainPic2.dispose();
        return image;
    }

    public static void createImage(BufferedImage image, File outFile) {
        if (image != null) {
            try {
                ImageIO.write(image, "jpg", outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getPicPath(java.util.List<com.jd.open.api.sdk.domain.ware.WareReadService.Image> images) {
        List<String> list = new ArrayList<>();
        if (images != null && images.size() > 0) {
            for (Image image : images) {
                list.add(IMAGE_PREFIX + image.getImgUrl());
            }
            return list;
        }
        return null;
    }
}
