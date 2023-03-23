# DataLake_Backend

The project uses Spring Boot framework and Hibernate for database connection.

Controller classes are the gateway for API calls from the frontend. These calls are passed on to the services layer where the business rules are located. When it comes to expanding and/or changing the code, we would recommend looking at the appropriate service class.

Dataset header combination for different datasets is made in the two switch cases located in the services class 'PatientService'. Dataset value combination and definition are being made in the entity class of the respective node. For example, the patient node class can be found in Entity/Nodes/Patient.java and modifications can be made here. The name of the dataset is also used in some cases to make a correction when two or more datasets have different meanings for the same value.

The pom.xml file contains all the dependencies the project uses, along with their respective versions. By loading Maven, these dependencies should be applied automatically. To run the project, execute the "DataLakeBackendApplication" function located in the "DataLakeBackendApplication" class.

The code contains Javadocs, and we have added extra comments where we felt they were needed. We have also included a short presentation that might help with understanding the application and its purpose.

The dataset we have used for development and testing purposes has been provided by "Project Data Sphere". Because these datasets are not public and have been provided to us through a user agreement, we have decided not to publicly release them on this GitHub. The access link for these datasets is provided below.

https://data.projectdatasphere.org/projectdatasphere/html/content/261
From dataset 261 was "c9732_demographic" used for the patient data and "c9732_ae" was used for symptoms

https://data.projectdatasphere.org/projectdatasphere/html/content/266
From dataset 266 was "c_chemo" aswell as "a_eendpt" used for patient and "c_ae" was used for symptoms


PS: We believe that a greater number of connections could be made between nodes from different datasets if the imports from the dataset were made to be case-insensitive. The change should be rather simple and could be a good starting point for learning how to modify the application.

Author infromation:<br>
Viktor Lindström Söraas, viktor.lindstrom00@gmail.com<br>
Kasper Lindström, Onsik1998@gmail.com<br>
Nonno Rydgren, nonnorydgren@gmail.com<br>
Jonathan Lindqvist, linkanjontes@gmail.com<br>

If you have any questions, feel free to send an email.
