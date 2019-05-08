package com.classmanagement.client.thread;




import com.classmanagement.client.bean.AdministratorLog;
import com.classmanagement.client.bean.ChatInfo;
import com.classmanagement.client.bean.Forum;
import com.classmanagement.client.bean.User;
import com.classmanagement.client.dao.GetData;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassManagement
 *
 * @author Richard-J
 * @description 服务器处理线程
 * @date 2019.04
 */

public class ChatHandleThread implements Runnable {
    DatagramSocket serverSocket;
    DatagramPacket pack;

    public ChatHandleThread(DatagramSocket serverSocket) {
        super();
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        OutputStream os = null;
        PrintStream ps = null;
        try {
            serverSocket.setReceiveBufferSize(1024 * 1024);
            serverSocket.setSendBufferSize(1024 * 1024);
            while (true) {
                byte[] b = new byte[1024 * 1024];
                //数据包
                pack = new DatagramPacket(b, b.length);
                serverSocket.receive(pack);

                //把字节数组转换成SendMsg对象
                //pack.getLength()接收到的字节数
                // 字节数组输入流
                ByteArrayInputStream bis = new ByteArrayInputStream(b, 0, pack.getLength());
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
                ChatInfo chatInfo = (ChatInfo) ois.readObject();

                if (chatInfo != null) {

                    //                test语句
                    System.out.println("发送者：" + chatInfo.getSelf().getStuNo());

                    //字节数组输出流
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(chatInfo);
                    //最终
                    b = bos.toByteArray();
                    //把要发送的信息转换为字节数组
                    //网络上发送任何东西都是发送字节数组
                    List<User> receivers = new ArrayList<>();
                    if (chatInfo.getType() == 0) {
                        receivers.add(chatInfo.getClassmate());
                    } else {

                        Forum forum = chatInfo.getForum();
                        int isClass = forum.getIsClass();
                        if (isClass == 1) {
                            receivers = GetData.getClassmate(forum.getId(), null);
                        } else {
                            receivers = GetData.getForumMember(forum.getId());
                        }
                    }

                    System.out.println(receivers.size());
                    for (int i = 0; i < receivers.size(); i++) {
                        InetAddress add = InetAddress.getByName(receivers.get(i).getNetAddress());
                        int port = receivers.get(i).getPort();
                        System.out.println(port);
                        DatagramPacket p = new DatagramPacket(b, 0, b.length, add, port);
                        //发送
                        serverSocket.send(p);
                    }

                } else {
                    pack = new DatagramPacket(b, b.length);
                    serverSocket.receive(pack);

                    //把字节数组转换成SendMsg对象
                    //pack.getLength()接收到的字节数
                    // 字节数组输入流
                    bis = new ByteArrayInputStream(b, 0, pack.getLength());
                    ois = new ObjectInputStream(new BufferedInputStream(bis));
                    AdministratorLog administratorLog = (AdministratorLog)ois.readObject();
                    if(administratorLog.getVote()==null){
                        com.classmanagement.client.server.Server.message.append(administratorLog.getAnnouncement().getSender()+"发布了公告："+administratorLog.getAnnouncement().getTitle()+"\n");
                        com.classmanagement.client.server.Server.message.append(administratorLog.getAnnouncement().getContent());
                        com.classmanagement.client.server.Server.message.append("\n");
                    }else {
                        com.classmanagement.client.server.Server.message.append(administratorLog.getVote().getSender()+"发起了投票："+administratorLog.getVote().getTitle()+"\n");
                        com.classmanagement.client.server.Server.message.append(administratorLog.getVote().getContent());
                        com.classmanagement.client.server.Server.message.append("\n");
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }

    }
}
