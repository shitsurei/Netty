package GoogleGRPC_08;

import GoogleGRPC_08.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
//        建立TCP连接
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext().build();
//        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);
////        普通调用
//        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("张三").build());
//        System.out.println(myResponse.getRealName());
//        System.out.println("-------------------------------");
////        流式返回值调用（返回对象是迭代器对象）
//        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(25).build());
//        while (iterator.hasNext()) {
//            StudentResponse response = iterator.next();
//            System.out.println(response.getName() + "---" + response.getAge() + "---" + response.getCity());
//        }
//        System.out.println("-------------------------------");
////        流式参数调用
//        StreamObserver<StudentResponseList> observer = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList value) {
//                for (StudentResponse response : value.getStudentResponseList()) {
//                    System.out.println(response.getName() + "--" + response.getAge() + "--" + response.getCity());
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("client completed!");
//            }
//        };
////        客户端向服务端以流式数据发请求，请求都是异步的
//        StudentServiceGrpc.StudentServiceStub serviceStub = StudentServiceGrpc.newStub(channel);
//        StreamObserver<StudentRequest> requestStreamObserver = serviceStub.getStudentsWrapperByAges(observer);
//        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
////        因为请求是异步的，如果立即执行complete方法，有可能请求线程还没有发送完成就关闭了客户端请求
//        requestStreamObserver.onCompleted();
//        System.out.println("-------------------------------");
//        双向流式调用
        StudentServiceGrpc.StudentServiceStub serviceStub = StudentServiceGrpc.newStub(channel);
        StreamObserver<StreamRequest> streamObserver = serviceStub.binaryTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                System.out.println("接收到服务端信息： " + value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("server completed!");
            }
        });
        for (int i = 0; i < 5; i++) {
            streamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
            TimeUnit.SECONDS.sleep(2);
        }
        streamObserver.onCompleted();
    }
}
