**Todo Backup Service:**

Provides straightforward async API to backup Todo items from external Todo Service

**API Endpoints:** 

`GET /backups` - returns list of backups.\
Status: Always OK (200)\
Example output: 
````
[{
  "backupId": 1,
  "date": "2017-01-01 18:00:00",
  "status": "IN_PROGRESS"
}]
````

`POST /backups` - starts a backup, returns started backup descriptor.\
Status: Always OK (200)\
Example output: 
````
{
  "backupId": 1,
  "date": "2017-01-01 18:00:00",
  "status": "IN_PROGRESS"
}
````

`GET /backups/{backupId}` - returns data associated with backup as a csv file. 
Status: 
 - OK(200) if successful 
 - NOT_FOUND(404) if backup doesn't exist
 - BAD_REQUEST (400) if backup is in progress or was unsuccessful
 
Example output (file):
````
Username;Subject;DueDate;Done
user1;u1-todo1;2018-01-01 12:00:00;true
user1;u1-todo2;2018-01-01 12:00:00;false
user2;u2-todo1;2018-01-01 12:00:00;true
user2;u2-todo2;2018-01-01 12:00:00;false
user2;u2-todo3;2018-01-01 12:00:00;false
````

**Configuration:**
 
 `todo.server.address` - specifies protocol and address of the Todo server.\
 Default value: `http//localhost:9000` 
 
 `server.port` - specifies to which port the application will bind its embedded HTTP server.\
 Default value: `8080`
 
Configuration can be overridden by placing application.properties file with overriding settings in same directory with the `todo-backup-service.jar` file.   

**How to start:**

To start the application execute following command from command line:
 
 `java -jar todo-backup-service.jar`

**How to build:**

Build requires maven installed in the system, to start build execute following command from command line in the project directory: 

`mvn install`

This will output executable JAR file with all dependencies to `{ProjectRoot}/target/todo-backup-service.jar`

**Dependencies:**
 
 In order to function application needs an external Todo service, which should output todo items in specific format (see dataset.json) from API endpoint `/users`.
