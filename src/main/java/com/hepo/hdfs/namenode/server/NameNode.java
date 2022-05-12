package com.hepo.hdfs.namenode.server;

/**
 * Description: namenode 核心启动类
 * Project:  hdfs_study
 * CreateDate: Created in 2022-04-22 09:54
 *
 * @author linhaibo
 */
public class NameNode {

    /**
     * 负责管理元数据的组件
     */
    private FSNamesystem namesystem;

    /**
     * namenode对外暴露rpc接口的server，可以相应请求
     */
    private NameNodeRpcServer rpcServer;

    private Boolean isRunning;

    public NameNode() {
        this.isRunning = true;
    }

    /**
     * 初始化namenode各个组件
     */
    public void initialize() {
        this.namesystem = new FSNamesystem();
        this.rpcServer = new NameNodeRpcServer(namesystem);
        this.rpcServer.start();
    }


    /**
     * NameNode启动服务的run方法
     */
    private void run() {
        while (isRunning) {
            try {
                System.out.println("NameNode组件已启动");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}