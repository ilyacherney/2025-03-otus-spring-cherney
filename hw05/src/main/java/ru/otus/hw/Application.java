package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// todo [x]: Предусмотреть операции вывода полного списка сущностей
		//  		[x] авторов
		//  		[x] жанров
		// todo [ ]: С помощью @JdbcTest сделать интеграционные тесты всех методов дао жанров (со встроенной БД)
		SpringApplication.run(Application.class, args);
	}

}
