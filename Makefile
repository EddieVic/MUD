# Makefile for the MUD
# File contributors: Eddie Vic

# Compile flags
JC=javac -d ./bin -cp ./bin
JAR=jar -cmf ./src/MANIFEST.MF Mud.jar -C ./bin ./mud

# List of .class files
CLASSFILES=bin/mud/controller/MudFrame.class bin/mud/model/Status.class bin/mud/model/PanelType.class bin/mud/view/ColumnPanel.class bin/mud/view/MudPanel.class bin/mud/view/RowPanel.class

# Make these always execute
.PHONY: all clean

# This is what is called when user runs 'make'
all: Mud.jar

# Compile each class file
bin/mud/controller/MudFrame.class: src/mud/controller/MudFrame.java  bin/ bin/mud/model/Status.class bin/mud/model/PanelType.class
	$(JC) src/mud/controller/MudFrame.java

bin/mud/model/Status.class: src/mud/model/Status.java bin/ bin/mud/view/ColumnPanel.class bin/mud/model/PanelType.class
	$(JC) src/mud/model/Status.java

bin/mud/model/PanelType.class: src/mud/model/PanelType.java
	$(JC) src/mud/model/PanelType.java

bin/mud/view/ColumnPanel.class: src/mud/view/ColumnPanel.java bin/
	$(JC) src/mud/view/ColumnPanel.java

bin/mud/view/MudPanel.class: src/mud/view/MudPanel.java bin/
	$(JC) src/mud/view/MudPanel.java

bin/mud/view/RowPanel.class: src/mud/view/RowPanel.java bin/
	$(JC) src/mud/view/RowPanel.java
# End of class files

# Make the bin directory if it doesn't exist
bin/:
	/bin/mkdir bin

# Compile the jar file with all the classes
Mud.jar: $(CLASSFILES) src/MANIFEST.MF
	$(JAR)
	/bin/chmod +x Mud.jar

# Delete all class files and the jar file
clean:
	/bin/rm -f -R bin Mud.jar mud.cfg