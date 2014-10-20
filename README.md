# java-starter-kit

Before you get started here, make sure you have Signed Up at http://monkeymusicchallenge.com. Also make sure that you have registered your team for the warmup and received your API key at http://warmup.monkeymusicchallenge.com. Then head over to your team page to get more detailed instructions on the game itself.

To understand this starter kit you will need to have basic knowledge of

* [java](https://www.java.com/en/)
* [maven](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

You will also need to have `java` and `maven` installed properly on your system.
Google can help you with this.

## Usage

First of all, `fork` this repository to your own github user.

Now open up a terminal and run the following commands:

```bash
git clone git@github.com:<username>/java-starter-kit.git
cd java-starter-kit
mvn package
java -cp target/mmc-bot-java-1.0-SNAPSHOT-jar-with-dependencies.jar main.java.monkeys.bot.Main <teamname> <apiKey>
```

These are all UNIX commands. Windows users are recommended [Cygwin](https://www.cygwin.com/) to make this work.

After running the above sequence, the monkey should be running around randomly on your team page.

The program should exit with a simple "Game over." statement. Now get coding!


## Get started

`Main.java` contains the boilerplate needed to communicate with the server. You should not need to change anything in here unless we have done something wrong.

`AI.java` is quite confused and seemingly random at the moment.

In order to complete the challenge, implement your AI so that `AI.move` makes the right choices. Pathfinding and AI ftw!


## Bugs

If you find any bugs in our code. Please submit an issue or a pull request to the original repository.
