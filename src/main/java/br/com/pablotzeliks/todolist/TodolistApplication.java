package br.com.pablotzeliks.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * Classe principal da aplicação TodoList.
 * <p>
 * Esta classe é o ponto de entrada da aplicação Spring Boot, responsável por inicializar
 * todos os componentes, configurações e contexto da aplicação de gerenciamento de tarefas.
 * </p>
 * <p>
 * A anotação {@code @SpringBootApplication} combina as seguintes configurações:
 * </p>
 * <ul>
 *   <li>{@code @Configuration}: Marca a classe como fonte de definições de beans.</li>
 *   <li>{@code @EnableAutoConfiguration}: Habilita a configuração automática do Spring Boot.</li>
 *   <li>{@code @ComponentScan}: Habilita a varredura de componentes no pacote atual e subpacotes.</li>
 * </ul>
 *
 * @author Pablo Tzeliks
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class TodolistApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 * <p>
	 * Este método delega a inicialização para {@link SpringApplication#run(Class, String...)},
	 * que configura o contexto da aplicação, inicia o servidor embutido e registra todos
	 * os beans gerenciados pelo Spring.
	 * </p>
	 *
	 * @param args argumentos de linha de comando passados durante a inicialização da aplicação
	 */
	public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		SpringApplication.run(TodolistApplication.class, args);
	}

}
