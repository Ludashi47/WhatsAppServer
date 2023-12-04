package service;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 該類對應的對象與客戶端保持通訊。
 */


public class ServerConnectClientThread extends Thread{
    //持有socket對象
    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {     //這裏綫程處於運行狀態，可以發送、接受訊息
        while(true){
            try {
                System.out.println("Hold Connection with "+userId);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //後續會使用到message
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
