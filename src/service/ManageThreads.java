package service;

import java.util.HashMap;

/**
 * 管理客戶端和伺服器端保持通訊的綫程的類
 */

public class ManageThreads {
    private static HashMap<String,ServerConnectClientThread> hm = new HashMap<>();

    //將某個客戶端的綫程加入到集合中
    public static void addThread(String userId, ServerConnectClientThread scct){
        hm.put(userId, scct);
    }

    //可以通過userId取得對應的綫程
    public static ServerConnectClientThread getServerConnectClientThread(String userId){
        return hm.get(userId);
    }
}
