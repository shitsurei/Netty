package GoogleProtobuf_06;

/**
 * 01
 * RPC（remote procedure call）：远程过程调用，支持跨语言
 * RMI（remote method invocation）：跨机器的方法调用，仅限于Java
 * 客户端将方法调用序列化成字节码的形式通过网络传输到服务器端，服务器端反序列化后将返回结果再序列化传回客户端
 * 代码生成：根据定义好的规范实现序列化（编码，EnCode），反序列化（解码，DeCode）和网络传输的过程
 * 客户端：stub
 * 服务端：skeleton
 *
 * RPC编写模式：
 * 1 定义一个接口说明文件：描述了对象（结构体）、对象成员和接口方法等一系列消息。
 * 2 通过RPC框架所提供的编译器将接口说明文件编译成具体语言文件
 * 3 在客户端与服务器端分别引入RPC编译器所生成的文件，即可像调用本地方法一样的调用远程方法
 *
 * Protobuf
 * 谷歌提供的语言中立，平台中立，用于序列化结构化数据的可扩展的机制，类似xml
 * 只需要一次定义结构化文件
 * 编写.proto文件
 * API采用方法链风格生成对象
 *
 * 使用Git作为版本控制工具在多个项目中管理相同的Proto文件
 * 方式1：使用git submodule（git仓库里的一个仓库）
 * ServerProject项目和ClientProject项目都将Protobuf-Java项目作为子模块
 * 问题：1 注意子模块和外层的git分支需要手动保持一致
 * 方式2：使用git subtree（避免多层仓库）
 */
public class Introduction {
//    在src目录下执行该命令生成DataInfo类文件
//    protoc --java_out=main/java main/resources/Protobuf/Student.proto
}
