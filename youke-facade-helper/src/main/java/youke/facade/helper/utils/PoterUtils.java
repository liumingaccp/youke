package youke.facade.helper.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/12
 * Time: 10:34
 */
public class PoterUtils {

    //========================新增参数=================
    private static int imageWidth = 720;  //图片的宽度
    private static int imageHeight = 1200; //图片的高度

    public static void graphicsGeneration(String name, String dateTime, InputStream headImg, InputStream mainImgurlInput, InputStream temInput, InputStream codeContentInput, File outFile) {
        BufferedImage bufferedImage = getBufferedImage(name, dateTime,headImg,mainImgurlInput, temInput, codeContentInput);
        createImage(bufferedImage, outFile);
    }


    private static BufferedImage getBufferedImage(String name, String dateTime, InputStream headImgInput, InputStream mainImgurlInput, InputStream temInput, InputStream codeContentInput) {
        BufferedImage image = null;

        int wx_width = 130;   //微信头像
        int H_mainPic = 600;            //主图高度
        int H_code = 240;              //二维码高度行业宽度
        int W_range_pic_pic = 70;       //图片与图片的间距宽度

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        //***********************设置微信头像
        Graphics wxPic = image.getGraphics();
        BufferedImage wxImg = null;
        try {
            wxImg = javax.imageio.ImageIO.read(headImgInput);
        } catch (Exception e) {}

        if(wxImg!=null){
            wxPic.drawImage(wxImg, 60, 35, wx_width, wx_width, null);
            wxPic.dispose();
        }

        //***********************设置推广语的标题
        //设置字体
        Graphics2D tip = image.createGraphics();//Microsoft YaHei
        tip.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tip.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font tipFont = new Font("微软雅黑", Font.PLAIN, 32);
        tip.setFont(tipFont);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.black);
        if(name != null && !name.equals("") && name.length() > 13){
            MyDrawString(name.substring(0,13), 85+ wx_width,(wx_width + 10 )/ 2 - 5 , 1.2 ,tip);
            if(name.length() > 13){ //如果成两页
                MyDrawString(name.substring(13), 85+ wx_width, (wx_width + 10 ) / 2 + 42, 1.2, tip);
            }
        }else {
            MyDrawString(name, 60 + 20 + wx_width, (wx_width + 10 ) / 2 , 1.2, tip);
        }
        //***********************插入中间广告图
        Graphics mainPic = image.getGraphics();
        BufferedImage bimg = null;
        try {
            bimg = javax.imageio.ImageIO.read(mainImgurlInput);
        } catch (Exception e) {}

        if(bimg!=null){
            mainPic.drawImage(bimg, 60, 60 + wx_width, H_mainPic, H_mainPic, null);
            mainPic.dispose();
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
        } catch (Exception e) {}

        if(bimg2!=null){
            mainPic2.drawImage(bimg2, 90, wx_width + W_range_pic_pic + H_mainPic+40 ,H_code, H_code, null);
        }
        if(bimg3!=null){
            mainPic2.drawImage(bimg3, 90 + H_code + 60, wx_width + W_range_pic_pic + H_mainPic+40 ,H_code, H_code, null);
        }
        mainPic2.dispose();

        //***********************二维码,和右边的提示图片底下的文字提示BOLD
        Graphics2D tip2 = image.createGraphics();//Microsoft YaHei
        Font tipFont2 = new Font("微软雅黑", Font.PLAIN, 40);
        tip2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tip2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        tip2.setFont(tipFont2);
        //设置字体颜色，先设置颜色，再填充内容
        tip2.setColor(Color.black);
        MyDrawString("长按识别二维码，查看分享内容", 80,  wx_width + W_range_pic_pic + H_mainPic + H_code + 110, 1, tip2);

        /*
        int heith = 10 + wx_heith + W_range_pic_pic + H_mainPic + H_code + 30 + 40;
        //底部颜色
        main.setColor(new Color(245,245,245));
        main.fillRect(0, 10 + wx_heith + W_range_pic_pic + H_mainPic + H_code + 40 + 30 + 20, imageWidth, imageHeight - heith - 10);

        //***********************底下的文字提示BOLD
        Graphics2D tip3 = image.createGraphics();//Microsoft YaHei
        Font tipFont3 = new Font("宋体", Font.PLAIN, 30);
        tip3.setFont(tipFont3);
        //设置字体颜色，先设置颜色，再填充内容
        tip3.setColor(Color.black);
        MyDrawString("请长按图片保存,保存后可调用相册分享推广", 60, heith + 40 + 30, 1, tip3);
        MyDrawString("海报有效期:"+dateTime+" 过期可重新下载", 60 + 40, heith + 40 + 30 + 40 + 10, 1, tip3);
        */
        return image;
    }



    //存入文件
    public static void createImage(BufferedImage image, File outFile) {
        if(image != null){
            try {
                ImageIO.write(image,"jpg",outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //可以控制间距
    public static void MyDrawString(String str,int x,int y,double rate, Graphics g){
        String tempStr=new String();
        int orgStringWight=g.getFontMetrics().stringWidth(str);
        int orgStringLength=str.length();
        int tempx=x;
        int tempy=y;
        while(str.length()>0)
        {
            tempStr=str.substring(0, 1);
            str=str.substring(1, str.length());
            g.drawString(tempStr, tempx, tempy);
            tempx=(int)(tempx+(double)orgStringWight/(double)orgStringLength*rate);
        }
    }

    public static void main(String[] args){
        File resultFile = null;
        InputStream posterStream = null;
        InputStream fingerStream = null;
        InputStream codeStream = null;
        InputStream wxHeadImgStream = null;
        String body = "春华秋实岁月无声阳光明媚山清水秀天地无疆";
        try {
            resultFile = new File("C:\\Users\\lenovo\\Desktop\\image\\taoke2.jpg");
            if(!resultFile.exists()){
                resultFile.createNewFile();
            }
            wxHeadImgStream = new FileInputStream("C:\\Users\\lenovo\\Desktop\\image\\9.jpg");
            posterStream = new FileInputStream("C:\\Users\\lenovo\\Desktop\\image\\poter.jpg");
            fingerStream = new FileInputStream("C:\\Users\\lenovo\\Desktop\\image\\finger.png");
            codeStream = new FileInputStream("C:\\Users\\lenovo\\Desktop\\image\\code.jpg");
            PoterUtils.graphicsGeneration(body,"2018-02-03", wxHeadImgStream, posterStream, fingerStream, codeStream, resultFile);
        }catch (Exception e){
            if(posterStream != null){
                try {
                    posterStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(fingerStream != null){
                try {
                    fingerStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(codeStream != null){
                try {
                    codeStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(wxHeadImgStream != null){
                try {
                    wxHeadImgStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
