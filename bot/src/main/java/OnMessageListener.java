import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.URI;
import java.net.URISyntaxException;


public class OnMessageListener extends ListenerAdapter {
    
    static String nameRoom = "NAME OF YOUR ROOM";

    @Override
    public void onMessageReceived(MessageReceivedEvent messageReceivedEvent) {

        if (messageReceivedEvent.getAuthor().isBot()) return;

        if (messageReceivedEvent.getChannel().getName().equals("irc")) {
            if ((!messageReceivedEvent.getMessage().getAuthor().getId().equals(messageReceivedEvent.getJDA().getSelfUser().getId()))) {
                String message = messageReceivedEvent.getMessage().getContentRaw();
                String author = messageReceivedEvent.getAuthor().getAsTag().split("#")[0];
                sendMessage(message, author);

            }
        }
    }
    WebsocketClientEndpoint clientEndPoint;

    void sendMessage(String message, String author) {

        if (message.equals("restart server") && clientEndPoint != null) {
            clientEndPoint.close();
            clientEndPoint.userSession = null;
            return;
        }

        try {

            if (clientEndPoint == null || clientEndPoint.userSession == null) {
                // open websocket
                clientEndPoint = new WebsocketClientEndpoint(new URI("wss://hack.chat/chat-ws"));
                clientEndPoint.sendMessage("{\"cmd\":\"join\",\"channel\":\""+nameRoom+"\",\"nick\":\"server\"}");
            }

            if (clientEndPoint.userSession != null) {
                clientEndPoint.sendMessage("{\"cmd\":\"chat\",\"text\":\""+author + ":" + message+"\"}");

            }
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }



    }





}