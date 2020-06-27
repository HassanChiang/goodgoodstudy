package com.fenxiangz.learn.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.ConfigResource;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KfkAdminApiTest {
    public static String TOPIC_NAME = "java-admin-api-test";

    public static void main(String[] args) {
        try {
            AdminClient adminClient = adminClient();
            System.out.println("===adminClient" + adminClient);
            createTopic(adminClient);
            describeTopics(adminClient);
            describeConfig(adminClient);
            listTopics(adminClient);
            deleteTopic(adminClient);
            listTopics(adminClient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTopic(AdminClient adminClient) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(TOPIC_NAME, 1, (short) 1);
        CreateTopicsResult topics = adminClient.createTopics(Arrays.asList(newTopic));
        for (Map.Entry<String, KafkaFuture<Void>> f : topics.values().entrySet()) {
            System.out.println("===createTopic:" + f.getValue().get());
        }
    }

    /**
     * java-api-test=(
     * name=java-api-test,
     * internal=false,
     * partitions=(
     * partition=0,
     * leader=hostName:9094 (id: 2 rack: null),
     * replicas=hostName:9094 (id: 2 rack: null),
     * isr=hostName:9094 (id: 2 rack: null)
     * ),
     * authorizedOperations=null
     * )
     *
     * @param adminClient
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void describeTopics(AdminClient adminClient) throws ExecutionException, InterruptedException {
        DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList(TOPIC_NAME));
        System.out.println("describeTopics===start");
        result.all().get().entrySet().forEach(System.out::println);
        System.out.println("describeTopics===end");
    }

    /**
     * ConfigResource(type=TOPIC, name='java-api-test')=
     * Config(entries=[
     *      ConfigEntry(name=compression.type, value=producer, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=leader.replication.throttled.replicas, value=, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=message.downconversion.enable, value=true, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=min.insync.replicas, value=1, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=segment.jitter.ms, value=0, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=cleanup.policy, value=delete, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=flush.ms, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=follower.replication.throttled.replicas, value=, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=segment.bytes, value=1073741824, source=STATIC_BROKER_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=retention.ms, value=604800000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=flush.messages, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=message.format.version, value=2.5-IV0, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=file.delete.delay.ms, value=60000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=max.compaction.lag.ms, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=max.message.bytes, value=1048588, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=min.compaction.lag.ms, value=0, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=message.timestamp.type, value=CreateTime, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=preallocate, value=false, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=min.cleanable.dirty.ratio, value=0.5, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=index.interval.bytes, value=4096, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=unclean.leader.election.enable, value=false, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=retention.bytes, value=-1, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=delete.retention.ms, value=86400000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=segment.ms, value=604800000, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=message.timestamp.difference.max.ms, value=9223372036854775807, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[]),
     *      ConfigEntry(name=segment.index.bytes, value=10485760, source=DEFAULT_CONFIG, isSensitive=false, isReadOnly=false, synonyms=[])
     *  ])
     * @param adminClient
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void describeConfig(AdminClient adminClient) throws ExecutionException, InterruptedException {
        DescribeConfigsResult result = adminClient.describeConfigs(
                Arrays.asList(new ConfigResource(ConfigResource.Type.TOPIC, TOPIC_NAME)));
        System.out.println("describeConfig===start");
        result.all().get().entrySet().forEach(System.out::println);
        System.out.println("describeConfig===end");
    }

    public static void listTopics(AdminClient adminClient) throws ExecutionException, InterruptedException {
        ListTopicsResult result = adminClient.listTopics();
        Set<String> names = result.names().get();
        System.out.println("===listTopics:" + names);

        ListTopicsResult result2 = adminClient.listTopics(
                new ListTopicsOptions()
                        .listInternal(true));
        KafkaFuture<Map<String, TopicListing>> mapKafkaFuture = result2.namesToListings();
        System.out.println("listTopics===start");
        mapKafkaFuture.get().entrySet().stream().forEach(System.out::println);
        System.out.println("listTopics===end");
    }

    public static void deleteTopic(AdminClient adminClient) throws ExecutionException, InterruptedException {
        DeleteTopicsResult result = adminClient.deleteTopics(Arrays.asList(TOPIC_NAME));
        for (Map.Entry<String, KafkaFuture<Void>> f : result.values().entrySet()) {
            System.out.println("===deleteTopic:" + f.getValue().get());
        }
    }

    public static AdminClient adminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }
}
