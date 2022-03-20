package br.com.kafkafull.producer

import br.com.kafkafull.model.Pessoa
import br.com.kafkafull.model.PessoaDTO
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import java.time.LocalDateTime

@Component
class PessoaProducerImpl(
    private val pessoaTemplate: KafkaTemplate<String, PessoaDTO>,

) {
    private val log = LoggerFactory.getLogger(PessoaProducerImpl::class.java)
    val topicName = "TopicPessoa"

    fun persist(messageId: String, payload: Pessoa){
        val dto = createDTO(payload)
        sendPessoaMessage(messageId, dto)
    }



    private fun createDTO(payload: Pessoa): PessoaDTO {

        return PessoaDTO.newBuilder()
            .setNome(payload.nome)
            .setSobrenome(payload.sobrenome)
            .build()

    }

    private fun sendPessoaMessage(messageId: String, dto: PessoaDTO) {
        val message = createaMessageWithHeaders(messageId, dto, topicName)
        val future: ListenableFuture<SendResult<String, PessoaDTO>> = pessoaTemplate.send(message)

        future.addCallback(object : ListenableFutureCallback<SendResult<String, PessoaDTO>> {
            override fun onSuccess(result: SendResult<String, PessoaDTO>?) {
               println("Dados enviado para o Kafka")
                log.info("Dados da pessoa enviados com sucesso com {} $messageId")
            }

            override fun onFailure(ex: Throwable) {
                println("Erro no envio dos dados")
                log.info("Erro no envio dos dados com {} $messageId")
            }
        })

    }

    private fun createaMessageWithHeaders(messageId: String, pessoaDTO: PessoaDTO, topic: String ): Message<PessoaDTO> {
        return MessageBuilder.withPayload(pessoaDTO)

            .setHeader( "hash", pessoaDTO.hashCode())
            .setHeader("version", "1.0.0")
            .setHeader("endofLife", LocalDateTime.now())
            .setHeader("type", "fct")
            .setHeader("cid", messageId)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader(KafkaHeaders.MESSAGE_KEY, messageId)
            .build()

    }


}