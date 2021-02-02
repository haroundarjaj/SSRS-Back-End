package com.haroun.ssrs.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
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
    public String readExcelFile(File file, int sheetNumber, int headerLine) {
        Workbook workbook = null;
        JsonObject sheetsJsonObject = new JsonObject();
        JsonArray sheetArray = new JsonArray();
        try {
            workbook = WorkbookFactory.create(file);
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            ArrayList<String> columnNames = new ArrayList<String>();
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            Iterator<Row> sheetIterator = sheet.iterator();
            for(int i = 0; i<headerLine-1; i++){
                sheetIterator.next();
                System.out.println("iteration " + i);
            }

            while (sheetIterator.hasNext()) {
                Row currentRow = sheetIterator.next();
                JsonObject jsonObject = new JsonObject();
                System.out.println("current row");
                System.out.println(currentRow.getRowNum());
                System.out.println("headerLine");
                System.out.println(headerLine-1);
                if (currentRow.getRowNum() != (headerLine-1)) {

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
                    System.out.println("columnNames");
                    System.out.println(columnNames);
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
    public String readTextFile(File file, boolean header, String separator) {
        JsonArray sheetArray = new JsonArray();
        String separatorDecoded = URLDecoder.decode("%" + separator);
        System.out.println(separator);
        BufferedReader br = null;
        String line = "";
        try {

                br = new BufferedReader(new FileReader(file));

            List <String> fieldNames = null;
            if(header) {
                if ((line = br.readLine()) != null) {
                    line = line.replace("\"","");
                    String[] items = line.split(separatorDecoded);
                    fieldNames = new ArrayList<>();
                    for (String elem: items
                         ) {
                        fieldNames.add(elem);
                    }
                }
            } else {
                fieldNames = new ArrayList<>();
                if ((line = br.readLine()) != null) {
                    JsonObject jsonObject = new JsonObject();
                    line = line.replace("\"","");
                    String[] items = line.split(separatorDecoded);
                    for (int i = 0; i < items.length ; i++) {
                        fieldNames.add("Field " + (i + 1));
                    }
                    for (int i = 0; i < fieldNames.size(); i++) {
                        jsonObject.addProperty(fieldNames.get(i), items[i]);
                    }
                    sheetArray.add(jsonObject);
                };
            }
            while (true) {
                if ((line = br.readLine()) == null) break;

                // use comma as separator
                line = line.replace("\"","");
                String[] item = line.split(separatorDecoded);
                JsonObject jsonObject = new JsonObject();
                for (int i = 0; i < fieldNames.size(); i++) {
                    jsonObject.addProperty(fieldNames.get(i), item[i]);
                }
                sheetArray.add(jsonObject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sheetArray.toString();
    }

    @Override
    public String readDwgFile(File file) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while((line=br.readLine())!= null){
                sb.append(line.trim());
            }
            System.out.println("string");
            System.out.println(sb);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public String readCsvFile(File file, boolean header) {
        JsonArray sheetArray = new JsonArray();
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

    @Override
    public String readXmlFile(File file) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while((line=br.readLine())!= null){
                sb.append(line.trim());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json = XML.toJSONObject(sb.toString());
        try {
            Iterator<String> keys = json.keys();
            String str_Name=keys.next();
            JSONObject firstElement = (JSONObject) json.get(str_Name);
            Iterator<String> keys2 = json.keys();
            String str_Name2 =keys2.next();
            JSONObject secondElement = (JSONObject) firstElement.get(str_Name2);
        } catch(JSONException je) {
            System.out.println(je.toString());
        }
        return json.toString();
    }
}
