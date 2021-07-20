package com.xiu.zk.client;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * 在 zk 原生客户端基础上封装的客户端
 *
 * @author yuesheng
 * @date 2021-07-20 16:49
 * @since 1.0
 */
@Slf4j
public class ZkClientDemo {
    private final static String CONNECT_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private final static String ZK_NODE = "/zk_clint_xiu";

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(CONNECT_STRING);
        ZkClient mySerializerClient = new ZkClient(CONNECT_STRING, 5000, 15000, new ZkSerializer() {
            @Override
            public byte[] serialize(Object o) throws ZkMarshallingError {
                return new byte[0];
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                return null;
            }
        });

        if (!zkClient.exists(ZK_NODE)) {
            zkClient.createPersistent(ZK_NODE, "ys");
            String child = zkClient.create(ZK_NODE + "/child", "child", CreateMode.PERSISTENT);
            log.info("创建子节点：{}", child);
        } else {
            Stat stat = zkClient.writeDataReturnStat(ZK_NODE, "ys", -1);
            log.info("存在节点信息：{}", stat);
        }

        List<String> children = zkClient.getChildren(ZK_NODE);
        log.info("子节点：{}", children);

        String data = zkClient.readData(ZK_NODE);
        log.info("节点 {} 的数据：{}", ZK_NODE, data);
    }
}
