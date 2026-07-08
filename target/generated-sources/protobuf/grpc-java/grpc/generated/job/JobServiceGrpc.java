package grpc.generated.job;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 *-------------------------------------
 *        SERVICE: JobService
 *-------------------------------------
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: job_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class JobServiceGrpc {

  private JobServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "job.JobService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.generated.job.AddJobRequest,
      grpc.generated.job.AddJobResponse> getAddJobMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddJob",
      requestType = grpc.generated.job.AddJobRequest.class,
      responseType = grpc.generated.job.AddJobResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.generated.job.AddJobRequest,
      grpc.generated.job.AddJobResponse> getAddJobMethod() {
    io.grpc.MethodDescriptor<grpc.generated.job.AddJobRequest, grpc.generated.job.AddJobResponse> getAddJobMethod;
    if ((getAddJobMethod = JobServiceGrpc.getAddJobMethod) == null) {
      synchronized (JobServiceGrpc.class) {
        if ((getAddJobMethod = JobServiceGrpc.getAddJobMethod) == null) {
          JobServiceGrpc.getAddJobMethod = getAddJobMethod =
              io.grpc.MethodDescriptor.<grpc.generated.job.AddJobRequest, grpc.generated.job.AddJobResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddJob"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.job.AddJobRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.job.AddJobResponse.getDefaultInstance()))
              .setSchemaDescriptor(new JobServiceMethodDescriptorSupplier("AddJob"))
              .build();
        }
      }
    }
    return getAddJobMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.generated.job.SearchJobPositionRequest,
      grpc.generated.job.Job> getSearchJobPositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SearchJobPosition",
      requestType = grpc.generated.job.SearchJobPositionRequest.class,
      responseType = grpc.generated.job.Job.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.generated.job.SearchJobPositionRequest,
      grpc.generated.job.Job> getSearchJobPositionMethod() {
    io.grpc.MethodDescriptor<grpc.generated.job.SearchJobPositionRequest, grpc.generated.job.Job> getSearchJobPositionMethod;
    if ((getSearchJobPositionMethod = JobServiceGrpc.getSearchJobPositionMethod) == null) {
      synchronized (JobServiceGrpc.class) {
        if ((getSearchJobPositionMethod = JobServiceGrpc.getSearchJobPositionMethod) == null) {
          JobServiceGrpc.getSearchJobPositionMethod = getSearchJobPositionMethod =
              io.grpc.MethodDescriptor.<grpc.generated.job.SearchJobPositionRequest, grpc.generated.job.Job>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SearchJobPosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.job.SearchJobPositionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.job.Job.getDefaultInstance()))
              .setSchemaDescriptor(new JobServiceMethodDescriptorSupplier("SearchJobPosition"))
              .build();
        }
      }
    }
    return getSearchJobPositionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static JobServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JobServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JobServiceStub>() {
        @java.lang.Override
        public JobServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JobServiceStub(channel, callOptions);
        }
      };
    return JobServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static JobServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JobServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JobServiceBlockingStub>() {
        @java.lang.Override
        public JobServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JobServiceBlockingStub(channel, callOptions);
        }
      };
    return JobServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static JobServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JobServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JobServiceFutureStub>() {
        @java.lang.Override
        public JobServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JobServiceFutureStub(channel, callOptions);
        }
      };
    return JobServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *-------------------------------------
   *        SERVICE: JobService
   *-------------------------------------
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void addJob(grpc.generated.job.AddJobRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.job.AddJobResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddJobMethod(), responseObserver);
    }

    /**
     */
    default void searchJobPosition(grpc.generated.job.SearchJobPositionRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.job.Job> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchJobPositionMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service JobService.
   * <pre>
   *-------------------------------------
   *        SERVICE: JobService
   *-------------------------------------
   * </pre>
   */
  public static abstract class JobServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return JobServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service JobService.
   * <pre>
   *-------------------------------------
   *        SERVICE: JobService
   *-------------------------------------
   * </pre>
   */
  public static final class JobServiceStub
      extends io.grpc.stub.AbstractAsyncStub<JobServiceStub> {
    private JobServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JobServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JobServiceStub(channel, callOptions);
    }

    /**
     */
    public void addJob(grpc.generated.job.AddJobRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.job.AddJobResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddJobMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchJobPosition(grpc.generated.job.SearchJobPositionRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.job.Job> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getSearchJobPositionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service JobService.
   * <pre>
   *-------------------------------------
   *        SERVICE: JobService
   *-------------------------------------
   * </pre>
   */
  public static final class JobServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<JobServiceBlockingStub> {
    private JobServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JobServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JobServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.generated.job.AddJobResponse addJob(grpc.generated.job.AddJobRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddJobMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<grpc.generated.job.Job> searchJobPosition(
        grpc.generated.job.SearchJobPositionRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getSearchJobPositionMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service JobService.
   * <pre>
   *-------------------------------------
   *        SERVICE: JobService
   *-------------------------------------
   * </pre>
   */
  public static final class JobServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<JobServiceFutureStub> {
    private JobServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JobServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JobServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.generated.job.AddJobResponse> addJob(
        grpc.generated.job.AddJobRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddJobMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_JOB = 0;
  private static final int METHODID_SEARCH_JOB_POSITION = 1;

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
        case METHODID_ADD_JOB:
          serviceImpl.addJob((grpc.generated.job.AddJobRequest) request,
              (io.grpc.stub.StreamObserver<grpc.generated.job.AddJobResponse>) responseObserver);
          break;
        case METHODID_SEARCH_JOB_POSITION:
          serviceImpl.searchJobPosition((grpc.generated.job.SearchJobPositionRequest) request,
              (io.grpc.stub.StreamObserver<grpc.generated.job.Job>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getAddJobMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.generated.job.AddJobRequest,
              grpc.generated.job.AddJobResponse>(
                service, METHODID_ADD_JOB)))
        .addMethod(
          getSearchJobPositionMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              grpc.generated.job.SearchJobPositionRequest,
              grpc.generated.job.Job>(
                service, METHODID_SEARCH_JOB_POSITION)))
        .build();
  }

  private static abstract class JobServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    JobServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.generated.job.JobServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("JobService");
    }
  }

  private static final class JobServiceFileDescriptorSupplier
      extends JobServiceBaseDescriptorSupplier {
    JobServiceFileDescriptorSupplier() {}
  }

  private static final class JobServiceMethodDescriptorSupplier
      extends JobServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    JobServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (JobServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new JobServiceFileDescriptorSupplier())
              .addMethod(getAddJobMethod())
              .addMethod(getSearchJobPositionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
