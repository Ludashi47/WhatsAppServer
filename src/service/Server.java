package service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伺服器，監聽9999端口，等待客戶端鏈接，保持通信
 */

public class Server {
    private ServerSocket ss = null;

    public Server() {
        //端口可以寫在 配置文件 中
        try {
            System.out.println("Server is listening on 9999...");
            ss = new ServerSocket(9999);

            while(true){    //監聽是循環的，當和某個客戶端連接後，會繼續監聽
                Socket socket = ss.accept();    //如果沒有客戶端連接，則會阻塞
                //得到socket關聯的對象輸入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User) ois.readObject();   //讀取客戶端發送的user對象

                //創建Message對象，準備回復客戶端
                Message message = new Message();
                //得到socket關聯的對象輸出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //驗證
                if(u.getUserId().equals("100") && u.getPasswd().equals("421414")){
                    //返回成功登入的消息
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //將message對象回復給客戶端
                    oos.writeObject(message);
                    //創建一個綫程，和客戶端保持通訊，該綫程需要持有socket對象
                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
                    //啟動該綫程
                    scct.start();
                    //將該綫程放入集合管理
                    ManageThreads.addThread(u.getUserId(), scct);
                }else{
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAILED);
                    //將message對象回復給客戶端
                    oos.writeObject(message);
                    socket.close();
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //如果伺服器退出while，説明伺服器不在監聽，關閉ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
