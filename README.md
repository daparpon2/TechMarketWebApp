# TechMarketWebApp

## Setup instructions
To run this software, you will need to follow these steps:
1. Start your preferred MySQL server instance. Create a new schema with name "techmarket".
2. Import or run the instructions within src/main/resources/db/techmarket.sql and src/main/resources/db/init.sql files, in that specific order.
3. Import the project code into NetBeans IDE. You will need to have previously installed an Apache Tomcat server and a Java JDK.
4. Fill in the static attributes of src/main/java/es/conselleria/daparpon/techmarket/utils/DBParams.java with the corresponding information of your MySQL server connection.
5. Run the "Clean and Build" Maven command to install all dependencies.
6. The project is ready to rumble!

## Disclaimer
Work developed for the final project of a Professional Formation Degree in Web App Development. All terms, names and descriptions are fictional. Any resemblance with individual, institution or company's names is pure coincidence.

## Attributions
This project is based on the TechMarket app available here: https://github.com/cesardl/tech-market-app.
