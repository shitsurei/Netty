package GoogleGRPC_08;

import GoogleGRPC_08.proto.MyRequest;
import GoogleGRPC_08.proto.MyResponse;
import GoogleGRPC_08.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
//        建立TCP连接
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext().build();
        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        MyResponse myResponse = blockingStub.getRealNameByUsername(MyRequest.newBuilder().setUsername("张三").build());
        System.out.println(myResponse.getRealname());
    }
}
