JAVA TECHNICAL ROUND
PROJECT DESCRIPTION:
You have been given a text data file containing earthquake data with 1 lakh records. Your task is to:

Parse the text data file in a Spring Boot application.
Save the parsed data into a database of your choice.
Create an API to retrieve records by eventID.

You are free to use any libraries, extensions, or tools that suit your development process.
The final solution should be pushed to a GitHub repository. You need to fork the repository, complete the task, and submit your work by 12:00 PM (Noon) on Monday, 23rd September.

REQUIREMENTS:
1. Parsing Text File:
The text file provided contains multiple records (1 lakh), each having the following columns:

eventID, datetime, latitude, longitude, magnitude, mag_type, depth, phasecount, azimuth_gap, location, agency, datetimeFM, latFM, lonFM, magFM, magTypeFM, depthFM, phasecountFM, AzGapFM, scalarMoment, Mrr, Mtt, Mpp, Mrt, Mrp, Mtp, varianceReduction, doubleCouple, clvd, strikeNP1, dipNP1, rakeNP1, strikeNP2, dipNP2, rakeNP2, azgapFM, misfit.


You need to parse this data using Spring Boot and map it to a database model.

2. Saving to Database:
Store the parsed data in a relational database of your choice (e.g., MySQL, PostgreSQL, etc.).
Ensure the database schema is optimized to handle large volumes of records (1 lakh+).

3. Creating an API:
Develop an API in Spring Boot to retrieve records by eventID.
The endpoint should allow for querying records with the following parameters:
eventID.
Return the corresponding data from the database for the given eventID.

DELIVERABLES:

1. Code Structure:
A well-structured Spring Boot project that includes the following:
Controller: To handle HTTP requests for the API.
Service: Business logic for handling data parsing and database interaction.
Repository: To interact with the database.
Model: Entity model(s) representing the data.

2. Database Schema:
Define the database schema, considering best practices for storing the provided data.

3. GitHub Repository:
Fork the repository.
Push your final code to your forked repository.
Share the link to your repository with us by 12 PM on Monday, 23rd September.

TOOLS & TECHNOLOGIES:

Spring Boot: For building the backend application.

Database: Any relational database of your choice.

Maven/Gradle: As the build tool.

JPA/Hibernate: For ORM and database interaction.

Any Library: Use any additional libraries required for parsing the text data file and other functionalities.
