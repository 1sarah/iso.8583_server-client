
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MTIClient implements ActionListener {
    JTextField outgoing;

    BufferedReader reader; // reads from server
    PrintWriter writer; // send data to server

    Socket sock;

    String host; // server location (ip address)

    int port;

    public MTIClient (String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run () {
        JFrame frame = new JFrame("MTI Client");

        JPanel mainPanel = new JPanel();

        outgoing = new JTextField(20);
        JButton sendButton = new JButton("send");
        sendButton.addActionListener(this);

        mainPanel.add(outgoing);
        mainPanel.add(sendButton);

        connectToServer(host, port);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 500);
        frame.setVisible(true);
    }

    public void connectToServer (String host, int port) {
        // make a socket, then make a PrintWriter
        // assign the PrintWriter to writer instance variable
        try {
            sock = new Socket(host, port); // connect to server
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);

            writer = new PrintWriter(sock.getOutputStream());

            System.out.println("Connection established...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed (ActionEvent ev) {
        // get text from text field
        // send it to server using writer

        writer.println(outgoing.getText()); // send to server
        writer.flush();

        outgoing.setText("");
        outgoing.requestFocus();
    }

}
