# Rosello Bot

## Information

Rosello is an echo-bot for [Discord](https://discordapp.com/). It was developed to have a fun and only :) To add it in your guild contact to me.  
Written by [MatrixDeity](https://github.com/MatrixDeity), 2018.

## Dependencies

The executable file needs *assets* folder with following text files:

* answers.dat
* answers_key.dat
* gags.dat
* greetings.dat
* welcomes.dat

The first two files must be divided into internal sections: every section has a personal name started by '#' at that each section into *answers.dat* must have a pair into *answers_key.dat*. Thus for keywords in *answers_key.dat* Rosello will find the random phrase from *answers.dat* by the section name.  
Example:  

*answers_key.dat*:
```md
#hello_section
hello
hi
hola
```

*answers.dat*:
```md
#hello_section
Hi, bro!
Hello, friend!
Greetings!
Glad see you!
```

If Rosello found any keywords from *answers_key.dat* into a user's message it send a random phrase from *answers.dat*.  
The following three files contain phrases for special events.

## Libraries

* [Discord4J](https://github.com/austinv11/Discord4J)