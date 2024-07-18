package ru.flamexander.transfer.service.core.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// -Dspring.profiles.active=dev

/*
	- Необходимо реализовать логику перевода средств между счетами:
		-V Перевод осуществляется по номеру счета
		-V Нельзя перевести средства на несуществующий счет
		-V Нельзя перевести средств больше, чем есть на счете отправителя
		-V Нельзя перевести отрицательное количество средств (затянуть к себе средства с чужого счета)
	- Все переводы должны сохраняться в БД:
	    -V id,
	    -V от кого - ид клиента-отправителя + внутренний ид счета,
	    -V кому - ид клиента-получателя + номер счета получателя,
	    -V сумма,
	    -V статус (создан, в обработке, выполнен, ошибка),
	    -V дата создания/последнего апдейта
	-V Должна быть возможность посмотреть список своих переводов
*/

@SpringBootApplication
public class JavaProSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaProSpringApplication.class, args);
	}
}
