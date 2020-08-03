package com.learn.shirol.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerifyCodeUtils {
    private static int width=90;
    private static int height=40;
    private static int codeCount=4;

    private static int xx=15;
    private static int fontHeight=30;
    private static int codeY=35;

    private static char[] codeSeq={
            'A','B','C','D','E','F','G','H', 'I','J','K','L','M','N','O','P', 'Q','R','S','T','U','V','W','X', 'Y','Z',
            'a','b','c','d','e','f','g','h', 'i','j','k','l','m','n','o','p', 'q','r','s','t','u','v','w','x', 'y','z',
            '0','1','2','3','4','5', '6','7','8','9'
    };

    public static Map<String,Object> genCAP(){
        //定义图像buffer
        BufferedImage buffImg= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Graphics gd = buffImg.getGraphics();
        //随机数生成器类
        Random random=new Random();
        //图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0,0,width,height);
        //字体
        Font font=new Font("Fixedsys",Font.BOLD,fontHeight);
        //设置字体
        gd.setFont(font);

        //画边框
        gd.setColor(Color.BLACK);
        gd.drawRect(0,0,width-1,height-1);

        //干扰线
        gd.setColor(Color.BLACK);
        for(int i=0;i<30;i++){
            int x=random.nextInt(width);
            int y=random.nextInt(height);
            int x1=random.nextInt(12);
            int y1=random.nextInt(12);
            gd.drawLine(x,y,x+x1,y+y1);
        }

        //保存随机产生的验证码
        StringBuffer randomCode=new StringBuffer();
        int red=0,green=0,blue=0;

        //随机产生验证码
        for(int i=0;i<codeCount;i++){
            //得到数字
            String code=String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
            //随机生成颜色
            red=random.nextInt(255);
            green=random.nextInt(255);
            blue=random.nextInt(255);

            //随机生成的颜色将验证码绘制到图像中
            gd.setColor(new Color(red,green,blue));
            gd.drawString(code,(i+1)*xx,codeY);

            //拼接
            randomCode.append(code);
        }

        Map<String,Object> map=new HashMap<>();

        map.put("code",randomCode);
        map.put("pic",buffImg);

        return map;
    }

    public static void main(String[] args) throws IOException {
        OutputStream out=new FileOutputStream("D://test.jpg");
        Map<String, Object> map = VerifyCodeUtils.genCAP();
        System.out.println(map.get("code"));
        ImageIO.write((RenderedImage) map.get("pic"),"jpeg",out);
    }
}
