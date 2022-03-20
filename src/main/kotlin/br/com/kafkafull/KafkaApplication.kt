package br.com.kafkafull

import br.com.kafkafull.model.Pessoa
import br.com.kafkafull.producer.PessoaProducerImpl
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaApplication


fun main(args: Array<String>) {
	runApplication<KafkaApplication>(*args)
}
