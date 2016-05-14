[![Build Status](https://travis-ci.org/Baseline-Team/baseline-core.svg?branch=v2.0-alpha)](https://travis-ci.org/google/guava)

# Baseline Project
**Version control and deployment system for Relational Databases**

Baseline Core - command line utility.

###Features:

* Database comparison. 
Tracking changes for a database schema & data:
    - between online databases
    - between an online database and an offline XML file
    - between offline XML files

* DDL & DML script generation. 
Generate scripts that:
    - update the target database to the source database state
    - revert changes in the source database to the target database state

* Support Foreign & Primary keys

* Export databases schema & data in XML format

* Workflow automation

* Configuration via XML file