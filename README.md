# thrift-server

"thrift-*.exe" -r -gen java ./*.thrift
生成 gen-java 目录

    Thrift是一个软件框架，用来进行可扩展且跨语言的服务的开发。它结合了功能强大的软件堆栈和代码生成引擎，以构建在 C++, Java, Python, PHP,
Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, and OCaml 等等编程语言间无缝结合的、高效的服务。

1.服务端编码基本步骤：

	* 实现服务处理接口impl
	* 创建TProcessor //读入，写出
	* 创建TServerTransport
	* 创建TProtocol
	* 创建TServer
	* 启动Server

2.客户端编码基本步骤：

	* 创建Transport
	* 创建TProtocol
	* 基于TTransport和TProtocol创建 Client
	* 调用Client的相应方法

3.数据传输协议

	* TBinaryProtocol : 二进制格式.
	* TCompactProtocol : 压缩格式
	* TJSONProtocol : JSON格式
	* TSimpleJSONProtocol : 提供JSON只写协议, 生成的文件很容易通过脚本语言解析

4.Server

	* TSimpleServer 单线程模式 测试用
	* TNonblockingServer NIO selector循环监听
	* THsHaServer 它使用一个单独的线程来处理网络I/O，一个独立的worker线程池来处理消息
	* TThreadedSelectorServer 非阻塞，用多个线程来处理网络I/O。它维护了两个线程池，一个用来处理网络I/O，另一个用来进行请求的处理
	* TThreadPoolServer 采用阻塞socket方式工作，,主线程负责阻塞式监听“监听socket”中是否有新socket到来，业务处理交由一个线程池来处理