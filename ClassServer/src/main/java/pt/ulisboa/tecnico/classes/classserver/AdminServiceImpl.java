package pt.ulisboa.tecnico.classes.classserver;


import io.grpc.stub.StreamObserver;
import pt.ulisboa.tecnico.classes.classserver.domain.ClassState;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.admin.AdminClassServer;
import pt.ulisboa.tecnico.classes.contract.admin.AdminServiceGrpc;
import java.util.logging.Logger;

public class AdminServiceImpl extends AdminServiceGrpc.AdminServiceImplBase {


    private static final Logger LOGGER = Logger.getLogger(AdminServiceImpl.class.getName());
    private ClassState _class;
    private final boolean DEBUG_VALUE;

    public AdminServiceImpl(ClassState _class, boolean debugValue) {
        this._class = _class;
        this.DEBUG_VALUE = debugValue;
    }


    @Override
    public void activate(AdminClassServer.ActivateRequest activateRequest,
                         StreamObserver<AdminClassServer.ActivateResponse> responseObserver) {

        debug("activate...");






    }

    @Override
    public  void deactivate(AdminClassServer.DeactivateRequest deactivateRequest,
                            StreamObserver<AdminClassServer.DeactivateResponse> responseObserver) {

        debug("deactivate...");
        
        
    }

    @Override
    public void activateGossip(AdminClassServer.ActivateGossipRequest activateGossipRequest,
                               StreamObserver<AdminClassServer.ActivateGossipResponse> responseObserver) {

        debug("activateGossip...");

    }

    @Override
    public void deactivateGossip(AdminClassServer.DeactivateGossipRequest deactivateGossipRequest,
                                 StreamObserver<AdminClassServer.DeactivateGossipResponse> responseObserver) {
        debug("deactivateGossip...");

    }

    @Override
    public void gossip(AdminClassServer.GossipRequest gossipRequest,
                       StreamObserver<AdminClassServer.GossipResponse> responseObserver) {

        debug("gossip...");

    }

    @Override
    public void dump(AdminClassServer.DumpRequest dumpRequest, StreamObserver<AdminClassServer.DumpResponse> responseObserver) {

        debug("dump...");

        debug(" 'dump' building the response");
        AdminClassServer.DumpResponse response = AdminClassServer.DumpResponse.newBuilder().setCode(
                ClassesDefinitions.ResponseCode.OK).setClassState(
                ClassesDefinitions.ClassState.newBuilder().setCapacity(_class.getCapacity()).setOpenEnrollments(
                        _class.getOpenEnrollments()).addAllEnrolled(Utils.StudentWrapper(
                        _class.getEnrolled())).addAllDiscarded(Utils.StudentWrapper(
                        _class.getDiscarded()))).build();

        debug(" 'dump' responding to the request");
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }


    public void debug(String msg) {
        if(DEBUG_VALUE) {
            LOGGER.info(msg);
        }
    }

}