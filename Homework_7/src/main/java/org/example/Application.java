package org.example;

import java.util.List;
import java.util.logging.Logger;

public class Application {
    // Домашнее задание:
    //V - Реализовать класс DbMigrator - он должен при старте создавать все необходимые таблицы из файла init.sql
    //V Доработать AbstractRepository
    //V - Доделать V findById(id), V findAll(), V update(), V deleteById(id), V deleteAll()
    //V - Сделать возможность указывать имя столбца таблицы для конкретного поля (например, поле accountType маппить на столбец с именем account_type)
    //V - Добавить проверки, если по какой-то причине невозможно проинициализировать репозиторий, необходимо бросать исключение, чтобы
    // программа завершила свою работу (в исключении надо объяснить что сломалось)
    //V - Работу с полями объектов выполнять только через геттеры/сеттеры

    public static void main(String[] args) {
        DataSource dataSource = null;
        Logger logger = Logger.getLogger(Application.class.getSimpleName());
        try {
            dataSource = new DataSource("jdbc:h2:file:./db;MODE=PostgreSQL");
            dataSource.connect();

            DbMigrator migrator = new DbMigrator(dataSource);
            migrator.migrate();

            AbstractRepository<User> repository = RepositoryFactory.createRepository(dataSource, User.class);
            User user = new User("bob", "123", "bob");
            repository.create(user);

            List<User> users = repository.findAll();
            users.forEach(item->logger.info(item.toString()));
            var updUser = repository.findById(10);
            if (updUser.isPresent()){
                logger.info(updUser.toString());
                updUser.get().setLogin("Not bob");
                updUser.get().setPassword("321");
                repository.update(updUser.get());
            }

            logger.info(repository.findById(10).toString());
            repository.deleteById(20);
            users = repository.findAll();
            users.forEach(item->logger.info(item.toString()));
            //repository.deleteAll();
            users = repository.findAll();
            users.forEach(item->logger.info(item.toString()));


            AbstractRepository<Account> accountAbstractRepository = RepositoryFactory.createRepository(dataSource, Account.class);
            Account account = new Account(100L, "credit", "blocked");
            accountAbstractRepository.create(account);
            List<Account> accounts = accountAbstractRepository.findAll();
            accounts.forEach(item->logger.info(item.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataSource.disconnect();
        }
    }
}
