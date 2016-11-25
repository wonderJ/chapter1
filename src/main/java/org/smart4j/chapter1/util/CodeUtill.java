package org.smart4j.chapter1.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by 陈亮 on 2016/11/25.
 */
public final class CodeUtill {
    //验证码字体
    private static Map<Integer, Font> fontMap = new HashMap<Integer, Font>();

    //随机数
    private static Random random = new Random();

    //不允许实例
    private CodeUtill() {
    }

    /**
     * 生成验证码图片(jpeg).
     *
     * @param code   验证码文字
     * @param width  宽度
     * @param height 高度
     * @return 验证码图片数据
     */
    public static byte[] generateCodeImage(String code, int width, int height) {
        int fontSize = (int) (height / 6.0F * 5.0F);
        int charSpace = 2;//字符间距

        //绘图字体
        Font font=fontMap.get(fontSize);
        if(font ==null){
            font=new Font("Arial",Font.LAYOUT_LEFT_TO_RIGHT | Font.BOLD,fontSize);
            fontMap.put(fontSize,font);
        }

        //绘图
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=image.createGraphics();
        //消除线段的锯齿状边缘
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform rst=g.getTransform();

        //背景
        return null ;
    }
}
