Introduction
------------

Todo...


Prerequisites
-------------
- Java JDK 5 or better (http://java.sun.com)
- Play framework 2.0


Building & running
------------------

To build and start the webapp, just open a terminal window and type "play run" in the moveit directory.

When started, you should be able to go to http://localhost:9000 and get to a page saying "Database 'default' needs evolution!". Press button "Apply this script now!" and the MoveIt login page should appear.


Developing
----------

To start modifying the code in your favourite IDE there are several alternatives.

1. If you use IntelliJ IDEA, run command "play idea" in the moveit directory. This will create .idea and .idea_modules directories. Now it's possible to open the project in IDEA using "File/Open Project..."

2. For Eclipse, run command "play eclipsify". You then need to import the application into your Workspace with the "File/Import/General/Existing project…" menu (compile your project first).
