package com.hepo.dfs.namenode.server;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存双缓冲
 *
 * @author linhaibo
 */
public class DoubleBuffer {

    /**
     * 单块editslog缓冲区的最大大小：默认是25kb
     */
    private static final Integer EDIT_LOG_BUFFER_LIMIT = 25 * 1024;
    /**
     * 是专门用来承载线程写入edits log
     */
    private EditBuffer currentBuffer = new EditBuffer();

    /**
     * 专门用来将数据同步到另外一块缓冲
     */
    private EditBuffer syncBuffer = new EditBuffer();

    /**
     * 当前该缓冲区最大的txid
     */
    private long startTxId = 1L;


    /**
     * 将editLog写到缓冲区
     *
     * @param log
     */
    public void write(EditLog log) throws IOException {
        currentBuffer.write(log);
    }

    /**
     * 判断一下当前的缓冲区是否写满了需要刷到磁盘上去
     *
     * @return
     */
    public boolean shouldSyncToDisk() {
        if (currentBuffer.size() >= EDIT_LOG_BUFFER_LIMIT) {
            return true;
        }
        return false;
    }

    /**
     * 交换两个缓冲区，为了同步内存数据到磁盘做准备
     */
    public void setReadyToSync() {
        EditBuffer temp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = temp;
    }

    /**
     * 将缓冲区刷到磁盘上
     */
    public void flush() throws IOException {
        syncBuffer.flush();
        syncBuffer.clear();
    }

    /**
     * editlog缓冲区
     */
    class EditBuffer {

        /**
         * 针对内存缓冲区字节数组输出流
         */
        ByteArrayOutputStream buffer;

        /**
         * 上一次刷盘的时候最大的txid
         */
        private long endTxid = 0L;


        public EditBuffer() {
            this.buffer = new ByteArrayOutputStream(EDIT_LOG_BUFFER_LIMIT * 2);
        }

        /**
         * 将日志写到缓冲中
         *
         * @param editLog 日志
         */
        public void write(EditLog editLog) throws IOException {
            endTxid = editLog.getTxid();
            buffer.write(editLog.getContent().getBytes());
            //写入换行符
            buffer.write("\n".getBytes());
            System.out.println("写入一条editlog: " + editLog.getContent() + ",当前缓冲区的大小是：" + size());
        }

        /**
         * 返回当前缓冲区大小
         *
         * @return
         */
        public long size() {
            return buffer.size();
        }

        /**
         * 清空缓冲区
         */
        public void clear() {
            buffer.reset();
        }

        /**
         * 将缓冲区的数据写到磁盘文件中去
         */
        public void flush() throws IOException {
            byte[] data = buffer.toByteArray();
            ByteBuffer dataBuffer = ByteBuffer.wrap(data);
            String editsLogFilePath = "/Users/linhaibo/Documents/tmp/edits-" + startTxId + "-" + endTxid + ".log";

            RandomAccessFile file = null;
            FileOutputStream out = null;
            FileChannel fileChannel = null;

            try {
                file = new RandomAccessFile(editsLogFilePath, "rw");
                out = new FileOutputStream(file.getFD());
                fileChannel = out.getChannel();

                fileChannel.write(dataBuffer);
                //强制刷盘
                fileChannel.force(false);
            } finally {
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (out != null) {
                    out.close();
                }
                if (file != null) {
                    file.close();
                }
            }

            startTxId = endTxid + 1;

        }
    }
}