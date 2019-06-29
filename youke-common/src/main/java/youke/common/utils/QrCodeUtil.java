package youke.common.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import youke.common.bean.BufferedImageLuminanceSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 9:10
 */
public class QrCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    /**
     * 生成二维码
     *
     * @param content      源内容
     * @param imgPath      生成二维码保存的路径
     * @param needCompress 是否要压缩
     * @return 返回二维码图片
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath, boolean needCompress, int xsize, int ysize) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, xsize, ysize,
                hints);
        //去白边
        //bitMatrix = deleteWhite(bitMatrix);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    //去白
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     * 在生成的二维码中插入图片
     *
     * @param source
     * @param imgPath
     * @param needCompress
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成带logo二维码，并保存到磁盘
     *
     * @param content
     * @param imgPath      logo图片
     * @param destPath
     * @param needCompress
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath, boolean needCompress, int xsize, int ysize) throws Exception {
        BufferedImage image = QrCodeUtil.createImage(content, imgPath, needCompress, xsize, ysize);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999) + ".jpg";//生成随机文件名
        if(destPath.contains(".")){
            ImageIO.write(image, FORMAT_NAME, new File(destPath));
        }else {
            ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
        }
    }

    /**
     * 生成带logo二维码，并写入输出流中
     *
     * @param content
     * @param imgPath      logo图片
     * @param needCompress
     * @throws Exception
     */
    public static OutputStream encodeStream(String content, String imgPath, boolean needCompress, int xsize, int ysize) throws Exception {
        BufferedImage image = QrCodeUtil.createImage(content, imgPath, needCompress, xsize, ysize);
        String file = new Random().nextInt(99999999) + ".jpg";//生成随机文件名
        FileOutputStream stream = new FileOutputStream(file);
        ImageIO.write(image, FORMAT_NAME, stream);
        return stream;
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir。(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode(String content, String imgPath, String destPath, int xsize, int ysize) throws Exception {
        QrCodeUtil.encode(content, imgPath, destPath, false, xsize, ysize);
    }

    public static void encode(String content, String destPath, boolean needCompress, int xsize, int ysize) throws Exception {
        QrCodeUtil.encode(content, null, destPath, needCompress, xsize, ysize);
    }

    public static void encode(String content, String destPath, int xsize, int ysize) throws Exception {
        QrCodeUtil.encode(content, null, destPath, false, xsize, ysize);
    }

    public static void encode(String content, String imgPath, OutputStream output, boolean needCompress, int xsize, int ysize)
            throws Exception {
        BufferedImage image = QrCodeUtil.createImage(content, imgPath, needCompress, xsize, ysize);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, OutputStream output, int xsize, int ysize) throws Exception {
        QrCodeUtil.encode(content, null, output, false, xsize, ysize);
    }


    /**
     * 从二维码中，解析数据
     *
     * @param file 二维码图片文件
     * @return 返回从二维码中解析到的数据值
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    public static String decode(String path) throws Exception {
        return QrCodeUtil.decode(new File(path));
    }

    public static void main(String[] args){
        try {
            QrCodeUtil.encode("春华秋实", null, "C:\\Users\\lenovo\\Desktop\\image", false, 200, 200);
        }   catch (Exception e){}
    }
}
