package grpc.generated.interview;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 *--------------------------------
 * SERVICE: InterviewService
 *--------------------------------
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: interview_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class InterviewServiceGrpc {

  private InterviewServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "interview.InterviewService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.generated.interview.InterviewRequest,
      grpc.generated.interview.InterviewResponse> getScheduleInterviewMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ScheduleInterview",
      requestType = grpc.generated.interview.InterviewRequest.class,
      responseType = grpc.generated.interview.InterviewResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.generated.interview.InterviewRequest,
      grpc.generated.interview.InterviewResponse> getScheduleInterviewMethod() {
    io.grpc.MethodDescriptor<grpc.generated.interview.InterviewRequest, grpc.generated.interview.InterviewResponse> getScheduleInterviewMethod;
    if ((getScheduleInterviewMethod = InterviewServiceGrpc.getScheduleInterviewMethod) == null) {
      synchronized (InterviewServiceGrpc.class) {
        if ((getScheduleInterviewMethod = InterviewServiceGrpc.getScheduleInterviewMethod) == null) {
          InterviewServiceGrpc.getScheduleInterviewMethod = getScheduleInterviewMethod =
              io.grpc.MethodDescriptor.<grpc.generated.interview.InterviewRequest, grpc.generated.interview.InterviewResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ScheduleInterview"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.interview.InterviewRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.interview.InterviewResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InterviewServiceMethodDescriptorSupplier("ScheduleInterview"))
              .build();
        }
      }
    }
    return getScheduleInterviewMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.generated.interview.InterviewMessage,
      grpc.generated.interview.InterviewMessage> getInterviewChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "InterviewChat",
      requestType = grpc.generated.interview.InterviewMessage.class,
      responseType = grpc.generated.interview.InterviewMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.generated.interview.InterviewMessage,
      grpc.generated.interview.InterviewMessage> getInterviewChatMethod() {
    io.grpc.MethodDescriptor<grpc.generated.interview.InterviewMessage, grpc.generated.interview.InterviewMessage> getInterviewChatMethod;
    if ((getInterviewChatMethod = InterviewServiceGrpc.getInterviewChatMethod) == null) {
      synchronized (InterviewServiceGrpc.class) {
        if ((getInterviewChatMethod = InterviewServiceGrpc.getInterviewChatMethod) == null) {
          InterviewServiceGrpc.getInterviewChatMethod = getInterviewChatMethod =
              io.grpc.MethodDescriptor.<grpc.generated.interview.InterviewMessage, grpc.generated.interview.InterviewMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "InterviewChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.interview.InterviewMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.interview.InterviewMessage.getDefaultInstance()))
              .setSchemaDescriptor(new InterviewServiceMethodDescriptorSupplier("InterviewChat"))
              .build();
        }
      }
    }
    return getInterviewChatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InterviewServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InterviewServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InterviewServiceStub>() {
        @java.lang.Override
        public InterviewServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InterviewServiceStub(channel, callOptions);
        }
      };
    return InterviewServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InterviewServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InterviewServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InterviewServiceBlockingStub>() {
        @java.lang.Override
        public InterviewServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InterviewServiceBlockingStub(channel, callOptions);
        }
      };
    return InterviewServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InterviewServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InterviewServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InterviewServiceFutureStub>() {
        @java.lang.Override
        public InterviewServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InterviewServiceFutureStub(channel, callOptions);
        }
      };
    return InterviewServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *--------------------------------
   * SERVICE: InterviewService
   *--------------------------------
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void scheduleInterview(grpc.generated.interview.InterviewRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getScheduleInterviewMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewMessage> interviewChat(
        io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getInterviewChatMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service InterviewService.
   * <pre>
   *--------------------------------
   * SERVICE: InterviewService
   *--------------------------------
   * </pre>
   */
  public static abstract class InterviewServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return InterviewServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service InterviewService.
   * <pre>
   *--------------------------------
   * SERVICE: InterviewService
   *--------------------------------
   * </pre>
   */
  public static final class InterviewServiceStub
      extends io.grpc.stub.AbstractAsyncStub<InterviewServiceStub> {
    private InterviewServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InterviewServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InterviewServiceStub(channel, callOptions);
    }

    /**
     */
    public void scheduleInterview(grpc.generated.interview.InterviewRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getScheduleInterviewMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewMessage> interviewChat(
        io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getInterviewChatMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service InterviewService.
   * <pre>
   *--------------------------------
   * SERVICE: InterviewService
   *--------------------------------
   * </pre>
   */
  public static final class InterviewServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<InterviewServiceBlockingStub> {
    private InterviewServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InterviewServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InterviewServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.generated.interview.InterviewResponse scheduleInterview(grpc.generated.interview.InterviewRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getScheduleInterviewMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service InterviewService.
   * <pre>
   *--------------------------------
   * SERVICE: InterviewService
   *--------------------------------
   * </pre>
   */
  public static final class InterviewServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<InterviewServiceFutureStub> {
    private InterviewServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InterviewServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InterviewServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.generated.interview.InterviewResponse> scheduleInterview(
        grpc.generated.interview.InterviewRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getScheduleInterviewMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SCHEDULE_INTERVIEW = 0;
  private static final int METHODID_INTERVIEW_CHAT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SCHEDULE_INTERVIEW:
          serviceImpl.scheduleInterview((grpc.generated.interview.InterviewRequest) request,
              (io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INTERVIEW_CHAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.interviewChat(
              (io.grpc.stub.StreamObserver<grpc.generated.interview.InterviewMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getScheduleInterviewMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.generated.interview.InterviewRequest,
              grpc.generated.interview.InterviewResponse>(
                service, METHODID_SCHEDULE_INTERVIEW)))
        .addMethod(
          getInterviewChatMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              grpc.generated.interview.InterviewMessage,
              grpc.generated.interview.InterviewMessage>(
                service, METHODID_INTERVIEW_CHAT)))
        .build();
  }

  private static abstract class InterviewServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InterviewServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.generated.interview.InterviewServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InterviewService");
    }
  }

  private static final class InterviewServiceFileDescriptorSupplier
      extends InterviewServiceBaseDescriptorSupplier {
    InterviewServiceFileDescriptorSupplier() {}
  }

  private static final class InterviewServiceMethodDescriptorSupplier
      extends InterviewServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    InterviewServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InterviewServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InterviewServiceFileDescriptorSupplier())
              .addMethod(getScheduleInterviewMethod())
              .addMethod(getInterviewChatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
