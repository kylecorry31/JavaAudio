# Java Audio

> Simple API for playing and recording audio in Java. **Only Supports .wav files.**

## Features

* Play (local) audio files
* Record audio

## Getting Started

### Download

Download the .jar file from the [releases page](https://github.com/kylecorry31/JavaAudio/releases) and add it to your build path of your Java project. (Supports JDK 1.8+)

### Example

```java
    Output.play("/res/starwars.wav");
    
    Input.record("recording.wav", 10);
```
