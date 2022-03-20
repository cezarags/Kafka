package br.com.kafkafull.controller

import br.com.kafkafull.model.Pessoa
import br.com.kafkafull.producer.PessoaProducerImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event")
class ControllerTest(
    val pessoaProducerImpl: PessoaProducerImpl
) {
    @PostMapping("/event")
    fun sendEvent(@RequestBody pessoa: Pessoa): ResponseEntity<Any>{
        pessoaProducerImpl.persist("123",pessoa)
        return ResponseEntity.ok().build()


    }
}