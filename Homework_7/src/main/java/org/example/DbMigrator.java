package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;

public class DbMigrator {
    private final DataSource dataSource;

    public DbMigrator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void migrate() throws IOException, SQLException {
        URL resource = DbMigrator.class.getResource("/init.sql");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
             Statement statement = dataSource.getConnection().createStatement();) {
            StringBuilder sqlQuery = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlQuery.append(line);
                if (line.trim().endsWith(";")) {
                    statement.execute(sqlQuery.toString());
                    sqlQuery.setLength(0);
                }
            }
        }
    }
}
