# Turmas

Distributed Systems Project 2021/2022

## Authors
 
**Group G32**

### Code Identification

In all source files (namely in the *groupId*s of the POMs), replace __GXX__ with your group identifier. The group
identifier consists of a G and the group number - always two digits. This change is important for code dependency
management, to ensure your code runs using the correct components and not someone else's.

### Team Members

*(fill the table below with the team members, and then delete this line)*

| Number | Name              | User                                      | Email                                        |
|--------|-------------------|-------------------------------------------|----------------------------------------------|
| 95529  | Carolina Baptista | <https://github.com/CarolinaBaptista98>   | <mailto:ana.p.baptista@tecnico.ulisboa.pt>   |
| 97281  | Allan Donizette   | <https://github.com/BobUser>              | <mailto:bob@tecnico.ulisboa.pt>              |
| 97326  | Martim Correia    | <https://github.com/CharlieUser>          | <mailto:charlie@tecnico.ulisboa.pt>          |

## Getting Started

The overall system is made up of several modules. The main server is the _ClassServer_. The clients are the _Student_,
the _Professor_ and the _Admin_. The definition of messages and services is in the _Contract_. The future naming server
is the _NamingServer_.

See the [Project Statement](https://github.com/tecnico-distsys/Turmas) or a complete domain and system description.

### Prerequisites

The Project is configured with Java 17 (which is only compatible with Maven >= 3.8), but if you want to use Java 11 you
can too, just downgrade the version in the POMs.

To confirm that you have them installed and which versions they are, run in the terminal:

```s
javac -version
mvn -version
```

### Installation

To compile and install all modules:

```s
mvn clean install
```

## Built With

* [Maven](https://maven.apache.org/) - Build and dependency management tool;
* [gRPC](https://grpc.io/) - RPC framework.