package GoogleGRPC_08;

import GoogleGRPC_08.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
//    普通调用
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息： " + request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealName("张三").build());
        responseObserver.onCompleted();
    }

    @Override
//    流式返回值
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接收到客户端信息： " + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(15).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(25).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(35).setCity("深圳").build());
        responseObserver.onCompleted();
    }

    @Override
//    流式参数
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("接收到客户端信息： " + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
//            客户端以流式将数据全部传给服务端之后返回一个结果
            public void onCompleted() {
                StudentResponse response1 = StudentResponse.newBuilder().setName("张三").setAge(11).setCity("北京").build();
                StudentResponse response2 = StudentResponse.newBuilder().setName("李四").setAge(15).setCity("上海").build();
//                构造返回值list
                StudentResponseList list = StudentResponseList.newBuilder().addStudentResponse(response1).addStudentResponse(response2).build();
                responseObserver.onNext(list);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
//    双向流式调用
    public StreamObserver<StreamRequest> binaryTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println("接收到客户端信息： " + value.getRequestInfo());
//                TODO:双向传输有问题
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
//                服务端关闭接受客户端输入的流
                responseObserver.onCompleted();
            }
        };
    }
}
