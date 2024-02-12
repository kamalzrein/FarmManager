# Cmpt 370 2023: Team 35


***


## Project Name: Farm Manager


## Authors
- [Aliyyah ](https://www.linkedin.com/)
- [Hui ](https://www.linkedin.com/)
- [Kamal Zrein](https://www.linkedin.com/in/kamalzrein/)
- [Shawn](https://www.linkedin.com/)
- [Theodore Buckley](https://www.linkedin.com/)
- [Uchechukwu UDENZE](https://www.linkedin.com/in/udenzeuchechukwu/)

## Description
This project was built to allow for a comprehensive management of a farm. It is an application that allows the user to log and monitor a multitude of farm operations.


## Features:

Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your project, this is a good place to list differentiating factors.
- Crop & chemical operations
  - Add crops to a field
  - Add chemicals to a field
  - View field crop and chemicals
- Bin operations
  - Adding/removing bins
  - Viewing current/previous Bin grain type
  - Viewing/adding/removing grain to bin
  - Check grain toughness and cleanliness
- Task Management
  - Add/update/view task
  - View/assign/unassign task to employee
  - Mark Task as Complete
  - view task list & completed task list
- User Management
  - Add/edit/view employee
  - View/assign/unassign employee to task
  - Remove/Promote User to owner
  - View employee list
- Field Operations
  - Add/edit/remove field
  - View/ harvest field
  - View field list
- History Management
  - View field history 
  - Graph data representation by year

## Market advantage
The program allows for the Management of bins and grain storage. According to our client, this feature is highly desired and existing applications on the market lack this feature. 
The history feature within the app is also of great utility to allow for deeper understanding of soil, farming practices, and harvest yield relations. 

## Tech Stack

- **Programming language:** [Java](https://dev.java/learn/)
- **Front end:**  [JavaFX](https://openjfx.io/)
- **Database:** [MongoDB Atlas](https://www.mongodb.com/atlas/database) 
- **Styling:** [Cascading Style Sheets](https://www.w3schools.com/css/)
- **Build Automation and Dependency management:** [Apache Maven](https://maven.apache.org/)




## Installation

REQUIRES JDK 21 TO RUN


Installation is done by simply opening the project in intellij from VCS, copying in the gitlab repository link, and running the maven dependencies. If intellij cannot discover the maven build. Launching the class Main located in the src/java/org/openjfx.javafxmavenarchetypes will allow you to launch the program in intellij. The program can also be installed in developer mode by downloading the zip folder of the main branch on gitlab. The contents are then unzipped and the project is opened in Intellij. In case the Intellij run button of the main java class is faded, in Intellij go to File -> Invalidates Caches ... -> tick the first, second and last boxes, and then click Invalidate and Restart.

Launching the jar version of the build can be done by opening the target folder and launching the javafx-maven-archetypes-1.0-SNAPSHOT-shaded.jar file with jdk 21 or later.
  
  Automated can be ran by going to UIController and uncommenting the threads in public static void main. It is a thread for test and UITest.
  Testing code can be viewed inside the TEST file

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support
For any technical support, please contact the development team on Gitlab or our links provided above. 

## Roadmap
For the future, if we get the chance we would like to incorporate a login system for better security and access control. We would also like to add a history search feature for quicker access to data. 
Another upgrade we would aim to incorporate is the use of better performing paid version of the MongoDB database services as to not bottleneck the speed of task execution. 

## Contributing
Contributions to the project are welcome granted 3/6 author access is given. 

To begin your contribution journey, make sure to follow the installation guide provided above an also watch the demo video explaining the process of installing and providing a brief overview of the code base and application usage. 


## Authors and acknowledgment
The contributions of the authors mentioned above were indisposable for the successful completion of this project. We acknowledge their great contributions. 
The guidance and assitace of Professor Zadia Codabux and the TA teams were also essential for project success.

## Project status
Our team sees a lot of potential for future expansion and improvement of the project. Regrettably, due to the time, budget, and personel limitations placed on the project due it being part of a deliverable for CMPT 370, the project will go on hold for the forseable future. 
