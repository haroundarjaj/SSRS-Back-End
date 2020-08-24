package com.haroun.ssrs.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class FileSourceServiceImpl implements FileSourceService {

    @Override
    public String readExcelFile(File file, int sheetNumber) {
        Workbook workbook = null;
        JsonObject sheetsJsonObject = new JsonObject();
        JsonArray sheetArray = new JsonArray();
        try {
            workbook = WorkbookFactory.create(file);
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            ArrayList<String> columnNames = new ArrayList<String>();
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            Iterator<Row> sheetIterator = sheet.iterator();

            while (sheetIterator.hasNext()) {
                Row currentRow = sheetIterator.next();
                JsonObject jsonObject = new JsonObject();

                if (currentRow.getRowNum() != 0) {

                    for (int j = 0; j < columnNames.size(); j++) {

                        if (currentRow.getCell(j) != null) {
                            if (currentRow.getCell(j).getCellTypeEnum() == CellType.STRING) {
                                jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getStringCellValue());
                            } else if (currentRow.getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
                                jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getNumericCellValue());
                            } else if (currentRow.getCell(j).getCellTypeEnum() == CellType.BOOLEAN) {
                                jsonObject.addProperty(columnNames.get(j), currentRow.getCell(j).getBooleanCellValue());
                            } else if (currentRow.getCell(j).getCellTypeEnum() == CellType.BLANK) {
                                jsonObject.addProperty(columnNames.get(j), "");
                            }
                        } else {
                            jsonObject.addProperty(columnNames.get(j), "");
                        }

                    }
                    sheetArray.add(jsonObject);
                } else {
                    // store column names
                    for (int k = 0; k < currentRow.getPhysicalNumberOfCells(); k++) {
                        columnNames.add(currentRow.getCell(k).getStringCellValue());
                    }
                }


                sheetsJsonObject.add(workbook.getSheetName(0), sheetArray);

            }

            System.out.println(sheetArray);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return sheetArray.toString();
    }

    @Override
    public String readTextFile(File file, String separator) {
        String separatorDecoded = URLDecoder.decode(separator);
        System.out.println(separator);
        List<String[]> data = new ArrayList<>();
        BufferedReader br = null;
        String line = "";

        try {

            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    if (!((line = br.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // use comma as separator
                line = line.replace("\"","");
                String[] item = line.split(separator);
                data.add(item);
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    @Override
    public String readCsvFile(File file, boolean header) {
        JsonArray sheetArray = new JsonArray();;
        try (InputStream in = new FileInputStream(file);) {
            CSV csv = new CSV(true, ',', in );
            List <String> fieldNames = null;
            if(header) {
                if (csv.hasNext()) fieldNames = new ArrayList<>(csv.next());
            } else {
                fieldNames = new ArrayList<>();
                if (csv.hasNext()) {
                    JsonObject jsonObject = new JsonObject();
                    List<String> elements = new ArrayList<>(csv.next());
                    System.out.println("elements " + elements.size());
                    for (int i = 0; i < elements.size() ; i++) {
                        fieldNames.add("Field " + (i + 1));
                    }
                    for (int i = 0; i < fieldNames.size(); i++) {
                        jsonObject.addProperty(fieldNames.get(i), elements.get(i));
                    }
                    sheetArray.add(jsonObject);
                };
            }
            while (csv.hasNext()) {
                List < String > x = csv.next();
                JsonObject jsonObject = new JsonObject();
                for (int i = 0; i < fieldNames.size(); i++) {
                    jsonObject.addProperty(fieldNames.get(i), x.get(i));
                }
                sheetArray.add(jsonObject);
            }
            System.out.println("return");
            System.out.println(sheetArray.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("return");
        System.out.println(sheetArray.toString());
        return sheetArray.toString();
    }
}
