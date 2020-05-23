package com.bignerdranch.travelcommunity.ui.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.FileOutputStream;

/**
 * @author zhongxinyu
 * @date 2020/5/22
 * GitHub:https://github.com/ZXY-stu/TravelCommunity.git
 **/
public class VideoToGif {
    /*private FileOutputStream out;
    public void initEncoder() {
         out = new FileOutputStream(OUTPUT_PATH);
        encoder = new GIFEncoder();
        encoder.setDelay(FRAME_DELAY); // GIF帧延迟
        encoder.setTransparent(Color.BLACK); // 背景色
        encoder.setOutputStream(out);
    }

    public void addFrameToEncoder(Bitmap frame){    // 获得图象所有像素值
        getImagePixels();    // 计算调色板
        generatePalette();    // 写入图象控制扩展
        writeGraphicCtrlExt();    // 写入图象标识符
        writeImageDesc();    // 写入局部调色板
        writePalette();    // 写入图象像素对应的调色板索引
        writePixels();    // 回收bitmap
        frame.recycle();
    }

    public void generateGif() {
        initEncoder();
        encoder.writeHeader();  // 写入GIF文件头
        encoder.writeLSD(); // 写入屏幕逻辑标识符
        encoder.writePalette(); // 写入全局调色板
        extractFrames();  // 抽取视频所有图象，并写入GIF
        encoder.writeTrailer();  // 写入GIF文件尾
        }

    public void extractFrames() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(INPUT_PATH);    long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));    for (int i = 0; i < duration; i += EXTRACT_DURATION) {
            Bitmap bitmap = retriever.getFrameAtTime(i * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
            Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, GIF_WIDTH, bitmap.getHeight() * GIF_WIDTH / bitmap.getWidth(), false); // 根据GIF的大小Resize Bitmap
            if (bitmap != scaleBitmap && !bitmap.isRecycled()) {
                bitmap.recycle(); // 回收原始Bitmap
            }
            addFrameToEncoder(scaleBitmap); // 将Bitmap图象传入GIFEncoder中进行编码
        }
    }


    public void writeHeader()  {
        writeString("GIF89a"); // 文件头，这里为了设置帧延迟时间，使用89a版本}private void writeString(String s)  {    for (int i = 0; i < s.length(); i++) {
        out.write((byte) s.charAt(i));
    }

    public void writeLSD() {    // GIF宽、高
        writeShort(width);
        writeShort(height);
        out.write((0x80 | // 1 : 全局调色板标志
                0x70 | // 2-4 : 颜色深度
                0x00 | // 5 : 分类标志
                7)); // 6-8 : 256色
        out.write(0); // 背景色索引值
        out.write(0); // 默认宽高比 1:1}private void writeShort(int value) {
        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
    }

    public void writeGraphicCtrlExt() {
        out.write(0x21);
        out.write(0xf9);
        out.write(4);    int transp = 1;  // 使用透明色
        int disp = 2;  // 处理方法为回复到背景色
        disp <<= 2; // 左移2位
        out.write(0 | // 1:3 保留位
                disp | // 4:6 图形处理方法
                0 | // 7 非用户输入
                transp); // 8 是否使用透明色
        writeShort(delay); // 帧图象之间的延迟
        out.write(transIndex); // 透明色的颜色索引
        out.write(0); // 块终止标志
         }

    public void writeImageDesc() {
        out.write(0x2c); // 帧图象开始标志0x2C
        writeShort(0); // 帧图象位置(0,0)
        writeShort(0);
        writeShort(width); // 帧图象大小
        writeShort(height);
        out.write(0x80 | // 1 使用局部调色板
                0 | // 2 交织方式为顺序方式
                0 | // 3 分类标志
                0 | // 4-5 保留位0
                7); // 局部调色板大小为256
         }

    public void writePixels() {    // indexedPixels为每个像素点的颜色索引
        LZWEncoder encoder = new LZWEncoder(width, height, indexedPixels, 8);    // LZW压缩，并写入到文件中
        encoder.encode(out);
    }

    public void writeTrailer() {
        out.write(0x3b); // 文件尾
        out.flush();
        out.close();
    }

*/
}
