package com.haroun.ssrs.service;

import java.io.File;

public interface FileSourceService {

    String readExcelFile(File file, int sheetNumber);

    String readTextFile(File file, String separator);

    String readCsvFile(File file, boolean header);
}
