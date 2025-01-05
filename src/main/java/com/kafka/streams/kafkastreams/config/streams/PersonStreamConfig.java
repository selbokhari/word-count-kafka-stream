package com.kafka.streams.kafkastreams.config.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Slf4j
public class PersonStreamConfig {

    @Bean
    public KStream<String, Long> personStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> personStream = streamsBuilder.stream("input-topic", Consumed.with(Serdes.String(), Serdes.String()));

        KTable<String, Long> wordCount = personStream
                .mapValues(textLine -> textLine.toLowerCase())
                .flatMapValues(lowerCaseTextLine -> Arrays.asList(lowerCaseTextLine.split(" ")))
                .groupBy((key, word) -> word, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.as("Counts"));

        wordCount.toStream().to("output-topic", Produced.with(Serdes.String(), Serdes.Long()));

        // trafficker la topology
        log.info("Topology description: {}", streamsBuilder.build().describe());

        return wordCount.toStream();
    }

}
