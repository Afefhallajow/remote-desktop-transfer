package project.listeners;

import com.nimbusds.jose.JOSEException;
import project.messages.Message;
import project.requests.Request;
import project.responses.Response;
import project.services.servicemessages.ServiceMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;

public class Socket_Listener extends Listener {

    private ServerSocket server ;
    private Socket uniqueClient;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private String  port;

    public Socket_Listener(String port) {

        this.port=port;
    }

    @Override
    public void fill_listen_information() throws IOException {

        server = new ServerSocket( Integer.parseInt(port) );

    }

    @Override
    public void run() {
        try {

        while (true){



                ////////////////intalize
                uniqueClient = server.accept();

                oos = new ObjectOutputStream(
                    uniqueClient.getOutputStream());


                ois = new ObjectInputStream(
                    uniqueClient.getInputStream());


                /////////////work


                Request request=(Request) ois.readObject();


                Message msg=transform_message(request);

                ServiceMessage serviceMessage=call_facade(msg);

                Response response=make_response(request.getType(),serviceMessage);

                oos.writeObject(response);


                ////////closes
                oos.close();
                ois.close();
                uniqueClient.close();


        }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }


}
