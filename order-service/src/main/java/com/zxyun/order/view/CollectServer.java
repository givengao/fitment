package com.zxyun.order.view;

import com.zxyun.order.view.constant.ServerProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @des:
 * @Author: given
 * @Date 2019/8/15 10:33
 */
public class CollectServer implements Runnable  {

    private ServerSocket server;

    private CollectFrame collectFrame;

    public void open () throws IOException {
        //展示界面
        view();
        //监听端口
        server = new ServerSocket(ServerProperties.port);

        while (true) {
            try {
                //获取套接字
                Socket socket = server.accept();

                String msg = readSocket(socket);

                collectFrame.displayData(msg);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void view () {
        collectFrame = new CollectFrame();
        collectFrame.open();
    }

    private String readSocket (Socket socket) throws IOException {
        String line;
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //由Socket对象得到输入流，并构造相应的BufferedReader对象
//        PrintWriter writer=new PrintWriter(socket.getOutputStream());
//        //由Socket对象得到输出流，并构造PrintWriter对象
//        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        //由系统标准输入设备构造BufferedReader对象

//        //从标准输入读入一字符串
//        //4、获取输出流，响应客户端的请求
//        while(!line.equals("end")){
//            //如果该字符串为 "bye"，则停止循环
//            writer.println(line);
//            //向客户端输出该字符串
//            writer.flush();
//            //刷新输出流，使Client马上收到该字符串
//            System.out.println("Server:"+line);
//            //在系统标准输出上打印读入的字符串
//            System.out.println("Client:"+in.readLine());
//            //从Client读入一字符串，并打印到标准输出上
//            line=br.readLine();
//            //从系统标准输入读入一字符串
//        } //继续循环

        return in.readLine();
    }

    public void close () {

    }

    @Override
    public void run() {
        try {
            this.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
