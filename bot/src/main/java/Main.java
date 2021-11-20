import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    private static JDA jda;
    static String token = "TOKEN OF YOUR BOT";
    public static void main(String[] args) {

        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException loginException) {
            loginException.printStackTrace();
        }

        jda.addEventListener(new OnMessageListener());

    }

}
