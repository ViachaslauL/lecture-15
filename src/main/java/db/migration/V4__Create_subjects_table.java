package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

public class V4__Create_subjects_table extends BaseJavaMigration {

    private final String sql = "CREATE TABLE IF NOT EXISTS `subjects`(" +
            "    `id`           int NOT NULL AUTO_INCREMENT," +
            "    `subject_name` varchar(32) NOT NULL," +
            "    PRIMARY KEY (`id`)" +
            ") ENGINE = InnoDB" +
            "  AUTO_INCREMENT = 0" +
            "  DEFAULT CHARSET = utf8mb4;";

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute(sql);
        }
    }
}
