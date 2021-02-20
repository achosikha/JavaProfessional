import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Server server;

    private DataInputStream in;
    private DataOutputStream out;
    private boolean running;

    private String myNickName;
    private int nicknameID = 0;

    private static int userNumber = 1;

    ClientHandler(Socket socket, Server server)
    {
        this.socket = socket;
        this.server = server;
        running = true;

        nicknameID++;
        myNickName = "User " + nicknameID;
    }

    @Override
    public void run() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF("/nickname");
            out.flush();
            String getMyNickName = in.readUTF();

            if (!getMyNickName.equals("nickname"))
            {
                getMyNickName = myNickName;
            }

            System.out.println(getMyNickName + " start processing...");

            while (running)
            {
                String msg = in.readUTF();

                if (msg.equals("/quit"))
                {
                    out.writeUTF(msg);
                } else if (msg.startsWith("/w"))
                {
                    String[] theMessage = msg.split(" ", 3);
                    server.sendPM (this.myNickName, theMessage[1], this.myNickName + " to " + theMessage[1] + ": " + theMessage[2]);
                } else
                {
                    server.broadCastMessage(msg);
                }
                System.out.println(Thread.currentThread().getName() + ": " + msg);
            }
        } catch (Exception e)
        {
            System.err.println("Handled connection was broken.");
            server.removeClient(this);
        }
    }

    public void sendMessage(String message) throws IOException {
            out.writeUTF(message);
            out.flush();
    }

    public String getMyNickName() {
        return myNickName;
    }
}