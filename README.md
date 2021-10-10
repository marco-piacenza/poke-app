# Poke-App
    
## How to run - Part 1 - Setup Vue.js & Spring Boot

### Prerequisites

- Java 11
- Maven
- npm
- vue-cli

### Install Java and set JAVA_HOME
To install Java see the guide here: [Install Java](https://java.com/en/download/help/download_options.html)

To set JAVA_HOME variable see this quick guide: [Set JAVA_HOME](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux)

### Install Maven
Maven is provided out-of-the-box using the mvnw script, so you can skip this part.

### Install npm/vue-cli

Execute the command(s) inside a terminal:

#### MacOSX

```
brew install node
npm install -g @vue/cli
```

#### Linux

```
sudo apt update
sudo apt install node
npm install -g @vue/cli
```

#### Windows

```
choco install npm
npm install -g @vue/cli
```

## How to run - Part 2 - App run

Inside the root directory, do: 

#### Windows
```
mvnw.cmd clean install
```

#### Other OS
```
mvnw clean install
```

Execute app using following command:

#### Windows
```
mvnw.cmd --projects backend spring-boot:run
```

#### Other OS
```
mvnw --projects backend spring-boot:run
```

Go to http://localhost:8098/
