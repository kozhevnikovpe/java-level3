package Lesson3.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Controller {
    String nick;

    @FXML
    VBox VboxChat;

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginField;

    @FXML
    TextField passwordField;

    @FXML
    ListView<String> clientList;

    private boolean isAuthorized;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if(!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    private String getLogFilename(){
        return "history_"+nick+".txt";
    }

    private ArrayList<String> getLastMessages(int n) throws IOException {
        ArrayList<String> al = new ArrayList<String>();
        FileInputStream fi = new FileInputStream(getLogFilename());
        BufferedReader br = new BufferedReader(new InputStreamReader(fi));

        String tmp;
        while ((tmp = br.readLine()) != null)
        {
            al.add(tmp);
            if (al.size() > n)
                al.remove(0);
        }
        //Collections.reverse(al);
        return  al;
    }

    private void logMessage(String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getLogFilename(),true))) {
            writer.write(message+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showHistory(){
        try {
            ArrayList<String> al = getLastMessages(100);
            for (String message:al) {
                showMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String message){
        Label label = new Label(message + "\n");
        VBox vBox = new VBox();

        if(message.startsWith(Controller.this.nick+":")) {
            vBox.setAlignment(Pos.TOP_RIGHT);
        } else {
            vBox.setAlignment(Pos.TOP_LEFT);
        }

        vBox.getChildren().add(label);
        VboxChat.getChildren().add(vBox);
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                String[] tokens = str.split(" ",2);
                                Controller.this.nick = tokens[1];
                                setAuthorized(true);
                                textArea.setVisible(false);
                                VboxChat.setVisible(true);
                                Platform.runLater(
                                        () -> {
                                            showHistory();
                                        });
                                break;
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/serverclosed")) break;
                            if (str.startsWith("/clientlist")) {
                                String[] tokens = str.split(" ");
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        clientList.getItems().clear();
                                        for (int i = 1; i < tokens.length; i++) {
                                            clientList.getItems().add(tokens[i]);
                                        }
                                    }
                                });
                            } else {
                                Platform.runLater(
                                    () -> {
                                        logMessage(str);
                                        showMessage(str);
                                    }
                                );
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Dispose() {
        System.out.println("Отправляем сообщение о закрытии");
        try {
            if(out != null) {
                out.writeUTF("/end");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if(socket == null || socket.isClosed()) {
            connect();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectClient(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2) {
            System.out.println("Двойной клик");
        }
    }

//    @FXML
//    private void closeButtonAction(){
//        // get a handle to the stage
//        Stage stage = (Stage) closeButton.getScene().getWindow();
//        out.writeUTF("/close")
//        // do what you have to do
//        stage.close();
//    }
}
