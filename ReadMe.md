### What

This project is used to show `System.property` and `args` in `public static void main(String[] args)`

### How

1. install jdk8 and maven3
2. run commands below (or import project to your favorite IDE, edit code and debug):

    ```bash
    git clone https://github.com/Anebrithien/meteotest.git
    cd meteotest
    maven package
    java -Djava.security.auth.login.config=aaa.conf -jar target/meteo-test-1.0-SNAPSHOT.jar|grep aaa
    java -jar target/meteo-test-1.0-SNAPSHOT.jar -Djava.security.auth.login.config=aaa.conf|grep aaa
    ```