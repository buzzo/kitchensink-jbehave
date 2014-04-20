package br.com.buzzo.jbehave.util;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.buzzo.jbehave.model.Member;

public class DatabaseUtil {

    public static void clearDatabase() throws Exception {
        // TODO assim que a base crescer será necessario uma logica para truncar as tabelas na ordem correta
        Class.forName("org.h2.Driver");
        final Connection conn = DriverManager.getConnection("jdbc:h2:/tmp/kitchensink-quickstart;AUTO_SERVER=TRUE", "sa", "sa");
        conn.createStatement().execute("TRUNCATE TABLE member");
        conn.close();
    }

    public static void save(final Member member) throws Exception {
        // TODO: considerar usar o hibernate e parametrizar o datasource
        Class.forName("org.h2.Driver");
        final Connection conn = DriverManager.getConnection("jdbc:h2:/tmp/kitchensink-quickstart;AUTO_SERVER=TRUE", "sa", "sa");
        conn.createStatement().execute(
                "INSERT INTO member (id, name, email, phone_number) VALUES (" + member.getId() + ", '" + member.getName() + "', '"
                        + member.getEmail() + "', '" + member.getPhoneNumber() + "')");
        conn.close();
    }

}
