package pt.ulisboa.tecnico.classes.admin;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.ClassState;
import pt.ulisboa.tecnico.classes.contract.admin.AdminClassServer.*;
import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc;

public class AdminFrontend implements AutoCloseable {
    private final ManagedChannel channel;
    private ManagedChannel channel_specific;
    private final AdminServiceGrpc.AdminServiceBlockingStub stub;
    private AdminServiceGrpc.AdminServiceBlockingStub stub_specific;

    public AdminFrontend(String host, int port) {

        // Channel is the abstraction to connect to a service endpoint.
        // Let us use plaintext communication because we do not have certificates.
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

        // Create a blocking stub.
        stub = AdminServiceGrpc.newBlockingStub(channel);
    }

    public ClassState getClassState(DumpResponse response) {
        return response.getClassState();
    }

    public int getCodeDump(DumpResponse response) {
        return response.getCodeValue();
    }

    public ActivateResponse setActivate(ActivateRequest request)
    {

        return stub_specific.activate(request);
    }

    public DeactivateResponse setDeactivate(DeactivateRequest request)
    {
        return stub_specific.deactivate(request);
    }

    public DumpResponse setDump(DumpRequest request)
    {

        return stub_specific.dump(request);
    }

    public void setupSpecificServer(String host,int port) {
        this.channel_specific = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.stub_specific = AdminServiceGrpc.newBlockingStub(channel_specific);
    }

    @Override
    public final void close() {
        channel.shutdown();
    }

    public ActivateGossipResponse setActivateGossip(ActivateGossipRequest req) {
        return stub_specific.activateGossip(req);
    }

    public DeactivateGossipResponse setDeactivateGossip(DeactivateGossipRequest req) {
        return stub_specific.deactivateGossip(req);
    }

    public GossipResponse setGossip(GossipRequest req) {
        return stub_specific.gossip(req);
    }

}