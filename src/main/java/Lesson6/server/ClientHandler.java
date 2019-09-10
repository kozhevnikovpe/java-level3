package Lesson6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Main server;
    private String nick;
    //List<String> blackList;

    public String getNick() {
        return nick;
    }

    public boolean checkBlackList(String nick) {
        //return blackList.contains(nick);
        return AuthService.hasBlackList(this.nick,nick);
    }

    public ClientHandler(Socket socket, Main server)  {
        try {
            //this.blackList = new ArrayList<>();
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            server.getLog().debug("Ошибка ввода-вывода",e);
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            server.getLog().debug("Ошибка ввода-вывода",e);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str = in.readUTF();
                if (str.startsWith("/auth")) {
                    String[] tokens = str.split(" ");
                    String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                    if (newNick != null) {
                        if (!server.isNickBusy(newNick)) {
                            sendMsg("/authok "+newNick);
                            nick = newNick;
                            server.subscribe(ClientHandler.this);
                            break;
                        } else {
                            sendMsg("Учетная запись уже используется");
                        }
                    } else {
                        sendMsg("Неверный логин/пароль");
                    }
                }
                server.broadcastMsg(ClientHandler.this, str);
            }

            while (true) {
                String str = in.readUTF();
                if (str.startsWith("/")) {
                    if (str.equals("/end")) {
                        out.writeUTF("/serverclosed");
                        break;
                    }
                    if (str.startsWith("/w ")) {
                        String[] tokens = str.split(" ", 3);
                        server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                    }
                    if (str.startsWith("/blacklist ")) {
                        String[] tokens = str.split(" ");
                        //blackList.add(tokens[1]);
                        AuthService.addBlackList(nick,tokens[1]);
                        sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                    }
                } else {
                    server.broadcastMsg(ClientHandler.this,nick + ": " + str);
                    server.getLog().info("Сообщение от клиента "+nick);
                }
            }
        } catch (IOException e) {
            server.getLog().debug("Ошибка ввода-вывода",e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                server.getLog().debug("Ошибка ввода-вывода",e);
            }
            try {
                out.close();
            } catch (IOException e) {
                server.getLog().debug("Ошибка ввода-вывода",e);
            }
            try {
                socket.close();
            } catch (IOException e) {
                server.getLog().debug("Ошибка ввода-вывода",e);
            }
        }
        server.unsubscribe(ClientHandler.this);
    }
}
