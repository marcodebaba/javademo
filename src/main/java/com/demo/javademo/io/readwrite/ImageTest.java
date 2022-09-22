package com.demo.javademo.io.readwrite;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author：marco.pan
 * @ClassName：ImageTest
 * @Description：图片读写操作
 * @date: 2021年11月03日 1:41 下午
 */
public class ImageTest {
    public static final String PIC_PATH = "图片地址";
    //"https://mmbiz.qpic.cn/mmbiz_png/UHKG18j8iasZm2XZtyI3tl6fVGBK6FoQupicB2zOv0D7aokicV7L1J63vSdXr5xbIGdwiaYTW2Eaqbkjy6IDzXGtLw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1";
    //"https://mmbiz.qpic.cn/mmbiz_png/UHKG18j8iasZm2XZtyI3tl6fVGBK6FoQu8jvra9Q3mic9licwzpmqNjvYqeHJ1EdRJHzIqXQG8JEjuRK4wT9Ku5Qw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1";

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();
        downloadToLocal(PIC_PATH, new File("pic20211103.jpg"));
        System.out.println("test1复制文件所需的时间：" + (System.currentTimeMillis() - begin));

//        long begin = System.currentTimeMillis();
//        //new一个URL对象
//        URL url = new URL(PIC_PATH);
//        //打开链接
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        //设置请求方式为"GET"
//        conn.setRequestMethod("GET");
//        //超时响应时间为5秒
//        conn.setConnectTimeout(5 * 1000);
//        //通过输入流获取图片数据
//        InputStream inStream = conn.getInputStream();
//        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
//        byte[] data = readInputStream(inStream);
//        //new一个文件对象用来保存图片，默认保存当前工程根目录
//        File imageFile = new File("pic20211103.jpg");
//        //创建输出流
//        BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(imageFile));
//        //FileOutputStream outStream = new FileOutputStream(imageFile);
//        //写入数据
//        outStream.write(data);
//        //关闭输出流
//        outStream.close();
//        System.out.println("test1复制文件所需的时间：" + (System.currentTimeMillis() - begin));
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 下载(网络图片)保存到本地（不改变原(图片)大小，原来是多大,下载下来后也是多大） - 要放在线程中进行下载
     *
     * @param picPath 网络(图片)下载地址
     * @param file    下载保存的文件   new File("/storage/emulated/0/kid.jpg")  或者  new File("/storage/emulated/0","kid.jpg") 都可以  --> 下载的图片命名为kid.jpg,保存到/storage/emulated/0路径下，也可以不写后缀
     * @return
     */
    public static File downloadToLocal(String picPath, File file) {
        try {
            // 统一资源
            URL url = new URL(picPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            long begin = System.currentTimeMillis();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            int size = 0;
            //int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                //len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                //System.out.println("下载了-------> " + len * 100 / fileLength + "%\n");
            }
            bin.close();
            out.close();
            System.out.println("保存文件耗时：" + (System.currentTimeMillis() - begin));
        } catch (Exception e) {   // 这里要开线程运行，否则会报异常android.os.NetworkOnMainThreadException
            e.printStackTrace();
        } finally {
            return file;
        }
    }
}
