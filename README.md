Allegro Geecon 2015 Challenge
=============================

This is our coding challenge prepared for Geecon 2015. In order to claim points from this challenge, you need to be
registered at `http://conf.allegro.tech.io` using your **Github** account.

## Task

Your task is to create simple transaction report based on data from 3 microservices:

* financial statistics service - returns list of user ids for which to generate report
* users service - contains user data (name)
* transactions service - contains user transactions

You receive report request, which contains basic description of scope of users that should be included in the report.
Based on this, fetch user ids from statistics service. The report (`Report` class) should contain information about
sum of all transactions for each user. User should have name, not only ID.

In order to do this, implement contents of `ReportGenerator` class. You might be forced to change contents of `*Repository`
classes as well. There is a suite of tests in `GeeconChallengeTest` class, that stubs external dependencies.
Making them all green means completing the challenge.

## Prerequisites

You only need Java 8 to solve this task.

## How to start?

* fork this repository and clone it
* code, code, code..
* check if tests are green by running `./gradlew test` (or using your favourite IDE)
* create pull-request, so Travis can start the build

Make it your own work - we don't like cheaters!