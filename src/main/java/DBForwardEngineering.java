import models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class DBForwardEngineering {

    static final String ENV_VAR_NAME = "DATABASE_URL";

    public static void main(String[] args) throws URISyntaxException {

        SessionFactory factory = buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Player player = new Player();
        player.setName("Pasha");

        session.save(player);

        transaction.commit();

        session.close();
        factory.close();
    }


    static SessionFactory buildSessionFactory() throws URISyntaxException {
        Configuration configuration = new Configuration();
        Map<String,String> info = getEnvVarData();
        for(var t : info.entrySet())
            configuration.setProperty(t.getKey(),t.getValue());

        configuration.configure(); //"hibernate.cfg.xml"
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).configure();
        return configuration.buildSessionFactory(builder.build());
    }

    static Map<String,String> getEnvVarData() throws URISyntaxException {
        Map<String,String> map = new HashMap<>();
        URI dbUri = new URI(System.getenv(ENV_VAR_NAME));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() +
                dbUri.getPath() + "?sslmode=require";
        map.put("hibernate.connection.url",dbUrl);
        map.put("hibernate.connection.username",username);
        map.put("hibernate.connection.password",password);
        return map;
    }
}
