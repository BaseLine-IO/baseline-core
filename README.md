# Baseline Project
Version control and deployment system for relation databases

Baseline Core - command line utility.

Features:

* Database comparison

Tracking changes for a database schema & data:
    - between online databases
    - between an online database and an offline XML file
    - between offline XML files

* DDL & DML script generation
Generate scripts that:
    - update the target database to the source database state
    - revert changes in the source database to the target database state
Support Foreign & Primary keys

* Export databases schema & data in XML format

* Workflow automation

* Configuration via XML file