CLI with databases for Rafael
=============

A command-line utility which reads a list of people from a JSON file and creates a DB, providing commands to manipulate the insides of the DB.

Preparing input data
-------------
The program performs operations on data that's read during start-up from a JSON file.

The input data format must match to the following example:

```
[
  {
    "first_name": "Ivan",
    "last_name": "Ivanov",
    "age": 34
  },
  {
    "first_name": "Sergey",
    "last_name": "Sergeev",
    "age": 23
  },
  {
    "first_name": "Dunya",
    "last_name": "Kulakova",
    "age": 13
  },
  {
    "first_name": "Sergey",
    "last_name": "Prokhorov",
    "age": 45
  },
  {
    "first_name": "Latif",
    "last_name": "Kydyrbekov",
    "age": 19
  },
  {
    "first_name": "Akylzhan",
    "last_name": "Kozhabekov",
    "age": 27
  }
]
```

Building and running
-------------

To build an executable jar, run the following in the command line:

```
mvn package
```

The executable jar file will be created in the ```target``` folder.

The application can be run with a java -jar command:

```
java -jar target/CLI_People-1.0.1.jar
```
