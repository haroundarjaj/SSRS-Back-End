package com.haroun.ssrs.controller;

import com.haroun.ssrs.service.FileSourceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = { "http://localhost:3001" })
@RestController
@RequestMapping("/import/file")
public class FileSourceController {

    private FileSourceService fileSourceService;

    public FileSourceController (FileSourceService fileSourceService){
        this.fileSourceService = fileSourceService;
    }


    @RequestMapping(value = "/excel&sheet={sheetNumber}", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String readExcelFile(@RequestParam(value = "file", required = true) MultipartFile multipartFile, @PathVariable("sheetNumber") int sheetNumber) throws IOException {
        System.out.println(multipartFile.getSize());
        System.out.println(multipartFile.getBytes());
        System.out.println(multipartFile.getContentType());
        File tempFile = File.createTempFile("multipartFileTmp", multipartFile.getName());

        tempFile.deleteOnExit();

        multipartFile.transferTo(tempFile);
        System.out.println("tempFile");
        System.out.println(tempFile);
        String data = fileSourceService.readExcelFile(tempFile, sheetNumber);

        System.out.println("data");
        System.out.println(data);
        tempFile.delete();
        return data;
    }

    @RequestMapping(value = "/csv&header={header}", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String readCsvFile(@RequestParam(value = "file", required = true) MultipartFile multipartFile, @PathVariable("header") boolean header) throws IOException {
        System.out.println(multipartFile.getSize());
        System.out.println(multipartFile.getBytes());
        System.out.println(multipartFile.getContentType());
        File tempFile = File.createTempFile("multipartFileTmp", multipartFile.getName());

        tempFile.deleteOnExit();

        multipartFile.transferTo(tempFile);

        System.out.println(tempFile);
        String data = fileSourceService.readCsvFile(tempFile, header);

        System.out.println("data");
        System.out.println(data);
        tempFile.delete();
        return data;
    }


}
