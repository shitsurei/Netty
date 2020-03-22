package ApacheThrift_07;

/**
 * 01
 * Thrift是一种支持不同语言开发的C/S架构的中间语言
 * IDL（Interface Description Language）
 *
 * 支持的数据类型：byte i16 i32 i64 double string
 * 支持的容器类型：list set map 支持除了service（类似Java中的interface）之外的任何类型，包括exception
 * 类型定义：支持typedef关键字，const关键字
 * 命名空间：namespace关键字
 * 文件包含：include关键字
 * 工作原理：
 * 1 数据发送方以特定格式编码，通过socket发送后接收方再解码
 * 2 由thrift文件生成双发语言的接口和model
 */
public class Introduction {
}
