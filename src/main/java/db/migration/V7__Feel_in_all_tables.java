package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class V7__Feel_in_all_tables extends BaseJavaMigration {
    private final List<String> sql = new ArrayList<>();

    @Override
    public void migrate(Context context) throws Exception {
        sql.add("DELETE FROM `students`;");
        sql.add("ALTER TABLE `students` AUTO_INCREMENT = 0;");

        sql.add("DELETE FROM `classes`;");
        sql.add("ALTER TABLE `classes` AUTO_INCREMENT = 0;");

        sql.add("DELETE FROM `teachers`;");
        sql.add("ALTER TABLE `teachers` AUTO_INCREMENT = 0;");

        sql.add("DELETE FROM `subjects`;");
        sql.add("ALTER TABLE `subjects` AUTO_INCREMENT = 0;");

        sql.add("DELETE FROM `marks`;");
        sql.add("ALTER TABLE `marks` AUTO_INCREMENT = 0;");

        sql.add("DELETE FROM `parents`;");
        sql.add("ALTER TABLE `parents` AUTO_INCREMENT = 0;");

        sql.add("INSERT INTO `classes` (`class_code`) " +
                "VALUES ('8A'), ('8B'), ('8C'), ('8D'), ('9A'), ('9B'), ('9C'), ('9D'), ('10A'), ('10B');");

        sql.add("INSERT INTO `subjects` (`subject_name`) VALUES " +
                "('algebra')," +
                "('physics')," +
                "('chemistry')," +
                "('history')," +
                "('biology')," +
                "('english')," +
                "('astronomy')," +
                "('geometry')," +
                "('physical Culture')," +
                "('geography');");

        sql.add("INSERT INTO `teachers` (`first_name`, `last_name`, `patronymic`, `subject_id`) VALUES " +
                "('Кулагин', 'Олег', 'Андреевич', '1')," +
                "('Костромитин', 'Тимофей', 'Ильич', '2')," +
                "('Костенкова', 'Анна', 'Михайловна', '3')," +
                "('Колчанова', 'Ольга', 'Викторовна', '4')," +
                "('Кожемяко', 'Сергей', 'Александрович', '5')," +
                "('Иванова', 'Дарья', 'Александровна', '6')," +
                "('Драчев', 'Антон', 'Евгеньевич', '7')," +
                "('Грушко', 'Константин', 'Сергеевич', '8')," +
                "('Веснин', 'Юрий', 'Александрович', '9')," +
                "('Васюков', 'Роман', 'Владимирович', '10');");

        sql.add("INSERT INTO `students` (`last_name`, `first_name`, `patronymic`, `class_id`) VALUES " +
                "('Куликов', 'Роман', 'Александрович', '1')," +
                "('Лимаренко', 'Дмитрий', 'Геннадьевич', '2')," +
                "('Макаров', 'Денис', 'Андреевич', '3')," +
                "('Нестеров', 'Никита', 'Алексеевич', '4')," +
                "('Николашкин', 'Виталий', 'Анатольевич', '5')," +
                "('Олейник', 'Янина', 'Олеговна', '6')," +
                "('Осипов', 'Никита', 'Андреевич', '7')," +
                "('Ровкова', 'Алеся', 'Сергеевна', '8')," +
                "('Свиридова', 'Анна', 'Андреевна', '9')," +
                "('Семенова', 'Татьяна', 'Дмитриевна', '10');");

        sql.add("INSERT INTO `parents` (`student_id`, `last_name`, `first_name`, `patronymic`) VALUES " +
                "('1', 'Алмасов', 'Эдгар', 'Фатуллаевич')," +
                "('2', 'Баранова', 'Ксения', 'Александровна')," +
                "('3', 'Головчанская', 'Анна', 'Игоревна')," +
                "('4', 'Зайцев', 'Ярослав', 'Викторович')," +
                "('5', 'Лачина', 'Кристина', 'Александровна')," +
                "('6', 'Лебедева', 'Александра', 'Игоревна')," +
                "('7', 'Мульманова', 'Виктория', 'Алексеевна')," +
                "('8', 'Петров', 'Александр', 'Алексеевич')," +
                "('9', 'Петрова', 'Алена', 'Юрьевна')," +
                "('10', 'Полина', 'Екатерина', 'Александровна');");

        sql.add("INSERT INTO `marks` (`student_id`, `mark`, `subject_id`, `date`) VALUES " +
                "('1', '7', '10', '2021-10-01')," +
                "('1', '6', '10', '2021-10-01')," +
                "('2', '8', '9', '2021-10-02')," +
                "('2', '5', '9', '2021-10-02')," +
                "('3', '7', '8', '2021-10-03')," +
                "('3', '9', '8', '2021-10-04')," +
                "('4', '6', '7', '2021-10-03')," +
                "('4', '8', '7', '2021-10-04')," +
                "('5', '9', '6', '2021-10-05')," +
                "('5', '5', '6', '2021-10-05')," +
                "('6', '7', '5', '2021-10-06')," +
                "('6', '8', '5', '2021-10-06')," +
                "('7', '6', '4', '2021-10-07')," +
                "('7', '9', '4', '2021-10-07')," +
                "('8', '5', '3', '2021-10-08')," +
                "('8', '7', '3', '2021-10-08'),(" +
                "'9', '4', '2', '2021-10-09')," +
                "('9', '6', '2', '2021-10-09')," +
                "('10', '9', '1', '2021-10-10')," +
                "('10', '8', '1', '2021-10-11');");

        try (Statement statement = context.getConnection().createStatement()) {
            for (String s: sql) {
                statement.executeUpdate(s);
            }
        }
    }
}
