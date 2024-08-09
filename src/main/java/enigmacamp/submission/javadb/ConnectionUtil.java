package enigmacamp.submission.javadb;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {

    private static final HikariDataSource dataSource;

//    Sesuaikan dengan konfigurasi database anda

    private static final String url = "jdbc:mysql://localhost:3306/{DB_NAME}";
    private static final String uname = "UNAME_DB";
    private static final String pass = "PASS_DB";

    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(url);
        config.setUsername(uname);
        config.setPassword(pass);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10*60_000);

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
