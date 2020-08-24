package com.haroun.ssrs.service;

import java.io.File;

public interface FileSourceService {

    String readExcelFile(File file, int sheetNumber);

    String readCsvFile(File file, boolean header);

    String readXmlFile(File file);

    String readTextFile(File file, boolean header, String separator);

    String readDwgFile(File file);
}
