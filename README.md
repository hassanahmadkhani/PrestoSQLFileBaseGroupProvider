# PrestoSQLFileBaseGroupProvider

# Overview

PrestoSQL Group Provider File is a PrestoSQL plugin to map user names to groups using a static file. 

Its an implementation of PrestoSQL Group Provider, which will map groups from file with users.
The groups can be further used for system and catalog authorization and RBAC implementation based on groups with Ranger.
This pluging tested with PrestoSQL 333 and Ranger 2.1 and works fine!


## Build

1. Install JDK 11.0.8+ 
2. Install Maven 3.6.2

```
mvn clean package
```


## Deploy

### Copy artifacts

Copy the following artifacts from build or from release to the PrestoSQL plugin folder (`<path_to_prestosql>/plugin/file/`)

```
target/presto-group-provider-file-1.0/*.jar
```

### Prepare configuration file

Create `<path_to_presto_config>/group-provider.properties` with the following required parameters, e.g.:

```
group-provider.name=file
file.group-file=/opt/prestosql-33/group-mapping.txt
```


Create `/opt/prestosql-33/group-mapping.txt` with the following user and group mappings, e.g.:
note: user2 is member of two group
```
developer=user1,user2
dw=user3,user2
bi=user5,user6
```
