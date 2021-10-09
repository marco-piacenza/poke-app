# Poke-App
    
## How to run - Part 1 - Setup Vue.js & Spring Boot

### Prerequisites

- Java 11
- Maven
- npm
- vue-cli

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

```
mvn clean install
```

Execute app using following command:

```
mvn --projects backend spring-boot:run
```

Go to http://localhost:8098/
