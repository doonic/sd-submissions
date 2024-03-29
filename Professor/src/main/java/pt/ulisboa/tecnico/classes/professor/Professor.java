package pt.ulisboa.tecnico.classes.professor;

import pt.ulisboa.tecnico.classes.NamingServerGlobalFrontend;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.OpenEnrollmentsRequest;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.OpenEnrollmentsResponse;
import pt.ulisboa.tecnico.classes.contract.professor.ProfessorClassServer.*;
import pt.ulisboa.tecnico.classes.Stringify;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.ResponseCode;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.ServerEntry;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer.LookupRequest;
import pt.ulisboa.tecnico.classes.contract.naming.ClassServerNamingServer.LookupResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.classes.Utilities;
import sun.misc.Signal;

import static pt.ulisboa.tecnico.classes.Utilities.*;

public class Professor {

  private static final String EXIT_CMD = "exit";
  private static final String LIST_CMD = "list";
  private static final String OPEN_ENR_CMD = "openEnrollments";
  private static final String CLOSE_ENR_CMD = "closeEnrollments";
  private static final String CANCEL_ENR_CMD = "cancelEnrollment";
  private static final Logger LOGGER = Logger.getLogger(Professor.class.getName());
  private static String debugInput;
  static boolean debugFlag = false;

  public static void debug(String msg) {
    if(debugFlag) {
      LOGGER.info(msg);
    }
  }


  public static void main(String[] args) {

    String host = NAMING_HOST;
    int port = NAMING_PORT;

    HashMap<String, ArrayList<ServerEntry>> servers = new HashMap<>();
    servers.put(SERVICE,new ArrayList<>());
    int p_count = 1;
    int s_count = 1;
    Utilities look = new Utilities();

    if (args.length == 1)
    {
      debugInput = args[0];
      if(debugInput.equals(DEBUG)) {
        debugFlag = true;
      }
    }

    debug("Creating Professor Frontend");
    try (ProfessorFrontend frontend = new ProfessorFrontend(host, port); Scanner scanner = new Scanner(System.in)) {

      Signal.handle(new Signal(SIGINT), sig -> {
        debug("SIGINT found");
        System.out.println(EXIT_PROFESSOR);
        frontend.close();
        System.exit(SUCCESS);
      });

      NamingServerGlobalFrontend global_frontend = new NamingServerGlobalFrontend(host,port) {
        @Override
        public ClassServerNamingServer.RegisterResponse register(ClassServerNamingServer.RegisterRequest request) {
          return null;
        }

        @Override
        public LookupResponse lookup(LookupRequest request) {
          return super.lookup(request);
        }

        @Override
        public ClassServerNamingServer.DeleteResponse delete(ClassServerNamingServer.DeleteRequest request) {
          return null;
        }
      };

      while (true) {
        System.out.print(PROMPT);
        try {

          String[] line = scanner.nextLine().split(" ");
          switch (line[0]) {
            case EXIT_CMD -> System.exit(SUCCESS);

            case LIST_CMD -> {
              debug("Invoking list command");
              ArrayList<String> result = look.set_address_server(SERVICE,p_count,s_count,servers,"");
              debug("Server set");
              if(result.get(2).equals(PRIMARY)) {
                debug("Increasing primary count");
                p_count++;
              }
              else {
                debug("Increasing secondary count");
                s_count++;
              }
              debug("Setting up specific stub");
              frontend.setupSpecificServer(result.get(0),Integer.parseInt(result.get(1)));
              debug("Specific stub set up");

              ListClassRequest list_req = ListClassRequest.newBuilder().build();
              ListClassResponse list_res = frontend.setListClass(list_req);
              if (ResponseCode.forNumber(frontend.getCode(list_res)) == ResponseCode.OK)
                System.out.println(Stringify.format(frontend.getClassState(list_res))+"\n");
              else if (ResponseCode.forNumber(frontend.getCode(list_res)) == ResponseCode.INACTIVE_SERVER)
                System.out.println(Stringify.format(ResponseCode.INACTIVE_SERVER)+"\n");
            }

            case LOOKUP_CMD -> {
              debug("Invoking lookup command");
              Arrays.stream(line[2].split(",")).
                      collect(Collectors.toCollection(ArrayList::new)).stream().forEach( qualifier -> {

              LookupRequest req = LookupRequest.newBuilder().setServiceName(line[1]).
                      addQualifiers(qualifier).build();

              LookupResponse res = global_frontend.lookup(req);

              res.getServerList().stream().forEach(server -> servers.get(line[1]).add(server));
                      });
              debug("Servers found");
            }

            case OPEN_ENR_CMD -> {
              debug("Invoking openEnrollments command");
              ArrayList<String> result = look.set_address_server(SERVICE,p_count,s_count,servers,"P");
              debug("Server set");
              if(result.get(2).equals(PRIMARY)) {
                debug("Increasing primary count");
                p_count++;
              }
              else {
                debug("Increasing secondary count");
                s_count++;
              }
              debug("Setting up specific stub");
              frontend.setupSpecificServer(result.get(0),Integer.parseInt(result.get(1)));
              debug("Specific stub set up");

              debug("Creating and sending OpenEnrollments request");
              int numStudents = Integer.parseInt(line[1]);
              OpenEnrollmentsRequest oe_req = OpenEnrollmentsRequest.newBuilder().setCapacity(numStudents).build();
              OpenEnrollmentsResponse oe_res = frontend.setOE(oe_req);
              debug("OpenEnrollments response arrived");
              System.out.println(Stringify.format(oe_res.getCode())+"\n");
            }

            case CLOSE_ENR_CMD -> {
              debug("Invoking closeEnrollments command");
              ArrayList<String> result = look.set_address_server(SERVICE,p_count,s_count,servers,"P");
              debug("Server set");
              if(result.get(2).equals(PRIMARY)) {
                debug("Increasing primary count");
                p_count++;
              }
              else {
                debug("Increasing secondary count");
                s_count++;
              }
              debug("Setting up specific stub");
              frontend.setupSpecificServer(result.get(0),Integer.parseInt(result.get(1)));
              debug("Specific stub set up");

              debug("Creating and sending CloseEnrollments request");
              CloseEnrollmentsRequest ce_req = CloseEnrollmentsRequest.newBuilder().build();
              CloseEnrollmentsResponse ce_res = frontend.setCE(ce_req);
              debug("CloseEnrollments response arrived");
              System.out.println(Stringify.format(ce_res.getCode())+"\n");
            }
            case CANCEL_ENR_CMD -> {
              debug("Invoking cancelEnrollment command");
              ArrayList<String> result = look.set_address_server(SERVICE,p_count,s_count,servers,"");
              debug("Server set");
              if(result.get(2).equals(PRIMARY)) {
                debug("Increasing primary count");
                p_count++;
              }
              else {
                debug("Increasing secondary count");
                s_count++;
              }
              debug("Setting up specific stub");
              frontend.setupSpecificServer(result.get(0),Integer.parseInt(result.get(1)));
              debug("Specific stub set up");

              debug("Creating and sending CancelEnrollment request");
              CancelEnrollmentRequest c_req = CancelEnrollmentRequest.newBuilder().setStudentId(line[1]).build();
              CancelEnrollmentResponse c_res = frontend.setCanEnr(c_req);
              debug("CancelEnrollment response arrived");
              System.out.println(Stringify.format(c_res.getCode())+"\n");
            }
            default -> System.out.println(Stringify.format(ResponseCode.UNRECOGNIZED)+"\n");
          }
        } catch (NullPointerException e) {
          System.err.println("Error: null pointer caught");
        }
        catch (NumberFormatException e) {
          System.err.println("Error: string given instead of a number");
        }
      }
    } finally {
      System.out.println("");
    }
  }

}
