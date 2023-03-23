# DataLake_Backend

The project uses Spring Boot framework and Hibernate for database connection.

Controller classes are the gateway for API calls from the frontend. These calls are passed on to the services layer where the business rules are located. When it comes to expanding and/or changing the code, we would recommend looking at the appropriate service class.

The Pom.xml file contains all the dependencies the project uses, along with their respective versions. By loading Maven, these dependencies should be applied automatically. To run the project, execute the "DataLakeBackendApplication" function located in the "DataLakeBackendApplication" class.

The code contains Javadocs, and we have added extra comments where we felt they were needed.

PS: We believe that a greater number of connections could be made between nodes from different datasets if the imports from the dataset were made to be case-insensitive. The change should be rather simple and could be a good starting point for learning how to modify the application.

Author infromation:
Viktor Lindström Söraas, viktor.lindstrom00@gmail.com
Kasper Lindström, Onsik1998@gmail.com 
Nonno Rydgren, nonnorydgren@gmail.com
Jonathan Lindqvist, linkanjontes@gmail.com
