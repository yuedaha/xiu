```
redis-cli --cluster create 172.18.0.3:6391 172.18.0.7:6392 172.18.0.4:6393 172.18.0.6:6394 172.18.0.2:6395 172.18.0.5:6396 --cluster-replicas 1

redis-cli -c ## 集群模式

127.0.0.1:6391> set test 'hello world'
-> Redirected to slot [6918] located at 172.18.0.7:6392
OK

172.18.0.7:6392> get test
"hello world"

127.0.0.1:6394> get test
-> Redirected to slot [6918] located at 172.18.0.7:6392
"hello world"
```
