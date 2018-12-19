### What

This project is used to show `ArrayIndexOutOfBoundsException` in `org/meteoinfo/data/meteodata/DrawMeteoData.java:850`

### How

1. install jdk8 and maven3
2. compile and install [wContour](https://github.com/meteoinfo/wContour)
3. compile and install [meteoInfoLib](https://github.com/meteoinfo/MeteoInfoLib)
4. run commands below (or import project to your favorite IDE, edit code and debug):

    ```bash
    git clone https://github.com/Anebrithien/meteotest.git
    cd meteotest
    maven package
    java -jar target/meteo-test-1.0-SNAPSHOT.jar -Dfile=data/some_strange_grid.txt
    ```
5. you would get outputs below:

    ```log
    try to read data/some_strange_grid.txt
    try to draw shadedLayer
    Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 11
            at org.meteoinfo.data.meteodata.DrawMeteoData.createShadedLayer(DrawMeteoData.java:853)
            at org.meteoinfo.data.meteodata.DrawMeteoData.createShadedLayer(DrawMeteoData.java:731)
            at com.tsingye.meteo.test.GridDataTest.main(GridDataTest.java:93)
    
    ```
    
### Why

1. when reached `DrawMeteoData.java:853`, two possibilities: 

    > `valueIdx < cValues.length - 1`, it's OK
    
    > `valueIdx > cValues.length - 1`, Exception!

2. the gridData is strange?
