package com.tsingye.meteo.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.meteoinfo.data.GridData;
import org.meteoinfo.data.meteodata.DrawMeteoData;
import org.meteoinfo.layer.VectorLayer;
import org.meteoinfo.legend.ColorBreak;
import org.meteoinfo.legend.LegendScheme;
import org.meteoinfo.projection.ProjectionInfo;
import org.meteoinfo.projection.proj4j.CRSFactory;

public class GridDataTest {

  private static final int X_LEN = 360;
  private static final double X_MIN = -2692500D;
  private static final double X_DELTA = 15000D;

  private static final int Y_LEN = 320;
  private static final double Y_MIN = -2392500D;
  private static final double Y_DELTA = 15000D;
  private static final double MISSING_VALUE = -9999D;

  private static final String PROJ_PARAMS = "+proj=lcc +lat_1=30.0 +lat_2=60.0 +lat_0=35.0 +lon_0=103.5 +x_0=15000 +y_0=15000 +units=m";
  private static final ProjectionInfo PROJECTION_INFO = new ProjectionInfo(
      new CRSFactory().createFromParameters("lcc", PROJ_PARAMS)
  );
  private static final double[] X_ARRAY = buildDimArray(X_MIN, X_DELTA, X_LEN);
  private static final double[] Y_ARRAY = buildDimArray(Y_MIN, Y_DELTA, Y_LEN);


  private static double[] buildDimArray(double start, double step, long maxSize) {
    return DoubleStream.iterate(start, d -> d + step)
        .limit(maxSize)
        .toArray();
  }

  public static GridData readFromTextFile(String textFile) {
    try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
      double[][] data = reader.lines()
          .map(s -> s.split(","))
          .map(strings -> Arrays.stream(strings).mapToDouble(Double::parseDouble))
          .map(DoubleStream::toArray)
          .toArray(double[][]::new);
      GridData gridData = new GridData();
      gridData.xArray = X_ARRAY;
      gridData.yArray = Y_ARRAY;
      gridData.data = data;
      gridData.projInfo = PROJECTION_INFO;
      gridData.missingValue = MISSING_VALUE;
      return gridData;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void writeToTextFile(GridData gridData, String filename) {
    try (PrintWriter writer = new PrintWriter(filename)) {
      double[][] data = gridData.data;
      Arrays.stream(data)
          .map(rowArray -> Arrays.stream(rowArray)
              .mapToObj(String::valueOf)
              .collect(Collectors.joining(",")))
          .forEach(writer::println);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String filename = null;
    if (args != null && args.length == 1 && args[0].startsWith("-Dfile=")) {
      filename = args[0].replace("-Dfile=", "");
    } else {
      System.out.println("can't get filename from args, back to default file");
      filename = "data/some_strange_grid.txt";
    }
    System.out.println("try to read " + filename);
    GridData gridData = readFromTextFile(filename);
    if (gridData == null) {
      System.err.println("read file failed!");
      System.exit(-1);
    } else {
      System.out.println("try to draw shadedLayer");
      VectorLayer shadedLayer = DrawMeteoData.createShadedLayer(gridData, "foo", "bar");
      System.out.println("success!");
      LegendScheme legendScheme = shadedLayer.getLegendScheme();
      List<ColorBreak> legendBreaks = legendScheme.getLegendBreaks();
      System.out.println("shadedLayer's legendScheme has " + legendBreaks.size() + " colorBreaks");
    }
  }

}
