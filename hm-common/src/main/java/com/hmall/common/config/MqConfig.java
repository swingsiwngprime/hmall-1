package com.hmall.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqConfig {
    private  final RabbitTemplate rabbitTemplate;
    @Bean
    public MessageConverter Jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("监听到了消息returnCallback");
            log.debug("交换机：{}",returned.getExchange());
            log.debug("routingKey：{}",returned.getRoutingKey());
            log.debug("message：{}",returned.getMessage());
            log.debug("replyCode：{}",returned.getReplyCode());
            log.debug("replyText：{}",returned.getReplyText());
        });
    }

/*    rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
        public void returnedMessage(ReturnedMessage returned) {

        }
    });*/
}
