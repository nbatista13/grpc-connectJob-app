package grpc.generated.candidate;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 *-------------------------------------
 *        SERVICE: CandidateService
 *-------------------------------------
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: candidate_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CandidateServiceGrpc {

  private CandidateServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "candidate.CandidateService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.generated.candidate.CandidateRequest,
      grpc.generated.candidate.CandidateResponse> getRegisterCandidateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterCandidate",
      requestType = grpc.generated.candidate.CandidateRequest.class,
      responseType = grpc.generated.candidate.CandidateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.generated.candidate.CandidateRequest,
      grpc.generated.candidate.CandidateResponse> getRegisterCandidateMethod() {
    io.grpc.MethodDescriptor<grpc.generated.candidate.CandidateRequest, grpc.generated.candidate.CandidateResponse> getRegisterCandidateMethod;
    if ((getRegisterCandidateMethod = CandidateServiceGrpc.getRegisterCandidateMethod) == null) {
      synchronized (CandidateServiceGrpc.class) {
        if ((getRegisterCandidateMethod = CandidateServiceGrpc.getRegisterCandidateMethod) == null) {
          CandidateServiceGrpc.getRegisterCandidateMethod = getRegisterCandidateMethod =
              io.grpc.MethodDescriptor.<grpc.generated.candidate.CandidateRequest, grpc.generated.candidate.CandidateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterCandidate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.candidate.CandidateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.candidate.CandidateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CandidateServiceMethodDescriptorSupplier("RegisterCandidate"))
              .build();
        }
      }
    }
    return getRegisterCandidateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.generated.candidate.Skill,
      grpc.generated.candidate.SkillSummary> getUploadSkillsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadSkills",
      requestType = grpc.generated.candidate.Skill.class,
      responseType = grpc.generated.candidate.SkillSummary.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.generated.candidate.Skill,
      grpc.generated.candidate.SkillSummary> getUploadSkillsMethod() {
    io.grpc.MethodDescriptor<grpc.generated.candidate.Skill, grpc.generated.candidate.SkillSummary> getUploadSkillsMethod;
    if ((getUploadSkillsMethod = CandidateServiceGrpc.getUploadSkillsMethod) == null) {
      synchronized (CandidateServiceGrpc.class) {
        if ((getUploadSkillsMethod = CandidateServiceGrpc.getUploadSkillsMethod) == null) {
          CandidateServiceGrpc.getUploadSkillsMethod = getUploadSkillsMethod =
              io.grpc.MethodDescriptor.<grpc.generated.candidate.Skill, grpc.generated.candidate.SkillSummary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadSkills"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.candidate.Skill.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.generated.candidate.SkillSummary.getDefaultInstance()))
              .setSchemaDescriptor(new CandidateServiceMethodDescriptorSupplier("UploadSkills"))
              .build();
        }
      }
    }
    return getUploadSkillsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CandidateServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CandidateServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CandidateServiceStub>() {
        @java.lang.Override
        public CandidateServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CandidateServiceStub(channel, callOptions);
        }
      };
    return CandidateServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CandidateServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CandidateServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CandidateServiceBlockingStub>() {
        @java.lang.Override
        public CandidateServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CandidateServiceBlockingStub(channel, callOptions);
        }
      };
    return CandidateServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CandidateServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CandidateServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CandidateServiceFutureStub>() {
        @java.lang.Override
        public CandidateServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CandidateServiceFutureStub(channel, callOptions);
        }
      };
    return CandidateServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   *-------------------------------------
   *        SERVICE: CandidateService
   *-------------------------------------
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void registerCandidate(grpc.generated.candidate.CandidateRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.candidate.CandidateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterCandidateMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<grpc.generated.candidate.Skill> uploadSkills(
        io.grpc.stub.StreamObserver<grpc.generated.candidate.SkillSummary> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getUploadSkillsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CandidateService.
   * <pre>
   *-------------------------------------
   *        SERVICE: CandidateService
   *-------------------------------------
   * </pre>
   */
  public static abstract class CandidateServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CandidateServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CandidateService.
   * <pre>
   *-------------------------------------
   *        SERVICE: CandidateService
   *-------------------------------------
   * </pre>
   */
  public static final class CandidateServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CandidateServiceStub> {
    private CandidateServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CandidateServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CandidateServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerCandidate(grpc.generated.candidate.CandidateRequest request,
        io.grpc.stub.StreamObserver<grpc.generated.candidate.CandidateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterCandidateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<grpc.generated.candidate.Skill> uploadSkills(
        io.grpc.stub.StreamObserver<grpc.generated.candidate.SkillSummary> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getUploadSkillsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CandidateService.
   * <pre>
   *-------------------------------------
   *        SERVICE: CandidateService
   *-------------------------------------
   * </pre>
   */
  public static final class CandidateServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CandidateServiceBlockingStub> {
    private CandidateServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CandidateServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CandidateServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.generated.candidate.CandidateResponse registerCandidate(grpc.generated.candidate.CandidateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterCandidateMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CandidateService.
   * <pre>
   *-------------------------------------
   *        SERVICE: CandidateService
   *-------------------------------------
   * </pre>
   */
  public static final class CandidateServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CandidateServiceFutureStub> {
    private CandidateServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CandidateServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CandidateServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.generated.candidate.CandidateResponse> registerCandidate(
        grpc.generated.candidate.CandidateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterCandidateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_CANDIDATE = 0;
  private static final int METHODID_UPLOAD_SKILLS = 1;

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
        case METHODID_REGISTER_CANDIDATE:
          serviceImpl.registerCandidate((grpc.generated.candidate.CandidateRequest) request,
              (io.grpc.stub.StreamObserver<grpc.generated.candidate.CandidateResponse>) responseObserver);
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
        case METHODID_UPLOAD_SKILLS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadSkills(
              (io.grpc.stub.StreamObserver<grpc.generated.candidate.SkillSummary>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterCandidateMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.generated.candidate.CandidateRequest,
              grpc.generated.candidate.CandidateResponse>(
                service, METHODID_REGISTER_CANDIDATE)))
        .addMethod(
          getUploadSkillsMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              grpc.generated.candidate.Skill,
              grpc.generated.candidate.SkillSummary>(
                service, METHODID_UPLOAD_SKILLS)))
        .build();
  }

  private static abstract class CandidateServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CandidateServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.generated.candidate.CandidateServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CandidateService");
    }
  }

  private static final class CandidateServiceFileDescriptorSupplier
      extends CandidateServiceBaseDescriptorSupplier {
    CandidateServiceFileDescriptorSupplier() {}
  }

  private static final class CandidateServiceMethodDescriptorSupplier
      extends CandidateServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CandidateServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (CandidateServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CandidateServiceFileDescriptorSupplier())
              .addMethod(getRegisterCandidateMethod())
              .addMethod(getUploadSkillsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
