package controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class BoardGameApplication implements CommandLineRunner{
	 public void run(String... strings) throws Exception
	 {
	        /* データの追加 */
	        /* 登録情報の確認
	        System.out.println("DB登録結果確認");
	        UserTableRepository.findAll().forEach(p -> System.out.println(p.toString()));
	        */
	}

	public static void main(String[] args) {
		SpringApplication.run(BoardGameApplication.class, args);
	}
}
