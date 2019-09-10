package Lesson6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public Logger getLog(){return log;}
    private Vector<ClientHandler> clients;

    ExecutorService clientThreads = Executors.newFixedThreadPool(10);

    public Main() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            AuthService.connection();
//            String str = AuthService.getNickByLoginAndPass("login1","pass1");
//            System.out.println(str);
            server = new ServerSocket(8189);
            log.info("Сервер запущен");

            while (true) {
                socket = server.accept();
                log.info("Клиент подключился");
                clientThreads.execute(new ClientHandler(socket, this));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientThreads.shutdown();
            try {
                socket.close();
            } catch (IOException e) {
                log.debug("Ошибка ввода-вывода",e);
            }
            try {
                server.close();
            } catch (IOException e) {
                log.debug("Ошибка ввода-вывода",e);
            }
            AuthService.disconnect();
        }
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientList();
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o : clients) {
            if (!o.checkBlackList(from.getNick())) {
                o.sendMsg(msg);
            }
        }
    }

    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                o.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате");
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }

        String out = sb.toString();

        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }
}
