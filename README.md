# Write Yourself a Git Implemented in Kotlin
This repository is a Kotlin implementation for https://wyag.thb.lt/

## Development
When you import the project into Intellij IDEA, dependencies are not visible and
auto completion does not work. To overcome this problem, simply run:

```
gradle idea
```

This will create `*.iml`, `*.ipr` and `*.iws` files and all the dependencies will be 
visible.

## Run
`compile.sh` and `run.sh` are helpers to compile and run the project. The binary is available
at `./build/install/wyagkt/bin/`. To learn the available options, run:

```
./build/install/wyagkt/bin/wyagkt --help
```

## Distribute
To distribute the binary, simply run the following gradle task and the output will
be available at `build/distributions/wyagkt.tar`

```
gradle distTar
```