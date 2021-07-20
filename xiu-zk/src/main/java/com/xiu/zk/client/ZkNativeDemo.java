package com.xiu.zk.client;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

/**
 * zk 原生客户端
 *
 * @author yuesheng
 * @date 2021-07-20 16:38
 * @since 1.0
 */
@Slf4j
public class ZkNativeDemo {
    private final static String CONNECT_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    private final static String ZK_NODE = "/xiu";

    private final static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT_STRING, 500000, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                if (Event.KeeperState.SyncConnected == state) {
                    log.info("连接成功");
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();

        Stat stat = zooKeeper.exists(ZK_NODE, false);
        if (Objects.isNull(stat)) {
            String path = zooKeeper.create(ZK_NODE, "ys".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            log.info("创建节点：{}", path);
            path = zooKeeper.create(ZK_NODE + "/child", "ys".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            log.info("创建子节点：{}", path);
        } else {
            log.info("节点状态：{}", JSON.toJSONString(stat));
            stat = zooKeeper.setData(ZK_NODE, "new".getBytes(StandardCharsets.UTF_8), stat.getVersion());
            log.info("节点更新状态：{}", JSON.toJSONString(stat));
        }
        byte[] data = zooKeeper.getData(ZK_NODE, false, stat);
        log.info("节点数据：{}", new String(data));
        List<String> children = zooKeeper.getChildren(ZK_NODE, false);
        log.info("节点所有子节点：{}", children);

    }

}
