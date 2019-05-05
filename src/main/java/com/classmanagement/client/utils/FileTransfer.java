//package com.classmanagement.client.utils;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//
///**
// * ClassManagement
// *
// * @author Richard-J
// * @description 文件传输
// * @date 2019.05
// */
//
//public class FileTransfer {
//    static DataOutputStream out;
//    static DataInputStream in;
//    static int fileupload(File file) {
//        try {
//            out.writeUTF("file");
//            out.writeInt(0);
//            out.writeUTF(file.getName());
//            out.writeLong(file.length());
//            //约定格式 标识符+文件名+文件大小+上传标识
//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] buffer = new byte[4096];
//            int length = 0;
//            int progress = 0;
//            while ((length = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
//                out.write(buffer, 0, length);
//                progress += length;
//                System.out.println("进度" + (double) progress / file.length());
//                out.flush();
//            }
//            fileInputStream.close();
//            System.out.print("文件上传成功！");
//            out.writeUTF("done");
//            return 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    static int fileDownload(String file) {
//        try {
//            out.writeUTF("file");
//            out.writeInt(2);
//            out.writeUTF(file);
//
//            String fileName = in.readUTF();
//            long fileLength = in.readLong();
//            FileOutputStream fos = new FileOutputStream(new File("t:\\client\\" + fileName));
//            byte[] buffer = new byte[4096];
//            int read = 0;
//            while (true) {
//                int length = 0;
//                length = in.read(buffer, 0, buffer.length);
//                read += length;
//                if (length < 0) break;
//                fos.write(buffer, 0, length);
//                fos.flush();
//                System.out.println("progress" + (double) read / fileLength);
//                if (read > fileLength) break;
//            }
//            System.out.print("get file" + fileName + "\n length" + fileLength);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//}
