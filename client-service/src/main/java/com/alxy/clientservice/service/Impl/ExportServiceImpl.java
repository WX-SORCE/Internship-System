package com.alxy.clientservice.service.Impl;

import com.alxy.clientservice.entiy.Client;
import com.alxy.clientservice.repository.ClientRepository;
import com.alxy.clientservice.service.ExportService;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void exportClientsByAdvisorToCSV(ByteArrayOutputStream outputStream, String advisorId) throws IOException {
        List<Client> clients = clientRepository.findClientsByAdvisorId(advisorId);
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream));

        // 写入表头
        String[] header = {"Client ID", "Name", "Gender", "Birthday", "Phone Number", "Email", "Nationality", "ID Type", "ID Number", "Income Level", "Risk Level", "Total Assets", "Register Date", "KYC Due Date", "Status", "Remarks", "Last Updated", "User ID"};
        writer.writeNext(header);

        // 写入数据
        for (Client client : clients) {
            String[] row = {
                    client.getClientId(),
                    client.getName(),
                    client.getGender(),
                    client.getBirthday() != null ? client.getBirthday().toString() : "",
                    client.getPhoneNumber(),
                    client.getEmail(),
                    client.getNationality(),
                    client.getIdType(),
                    client.getIdNumber(),
                    String.valueOf(client.getIncomeLevel()),
                    String.valueOf(client.getRiskLevel()),
                    client.getTotalAssets() != null ? client.getTotalAssets().toString() : "",
                    client.getRegisterDate() != null ? client.getRegisterDate().toString() : "",
                    client.getKycDueDate() != null ? client.getKycDueDate().toString() : "",
                    client.getStatus(),
                    client.getRemarks(),
                    client.getLastUpdated() != null ? client.getLastUpdated().toString() : "",
                    client.getUserId()
            };
            writer.writeNext(row);
        }

        writer.close();
    }

    @Override
    public void exportClientsByAdvisorToExcel(ByteArrayOutputStream outputStream, String advisorId) throws IOException {
        List<Client> clients = clientRepository.findClientsByAdvisorId(advisorId);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] header = {"Client ID", "Name", "Gender", "Birthday", "Phone Number", "Email", "Nationality", "ID Type", "ID Number", "Income Level", "Risk Level", "Total Assets", "Register Date", "KYC Due Date", "Status", "Remarks", "Last Updated", "User ID"};
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
        }

        // 写入数据
        int rowNum = 1;
        for (Client client : clients) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(client.getClientId());
            row.createCell(1).setCellValue(client.getName() != null ? client.getName() : "");
            row.createCell(2).setCellValue(client.getGender() != null ? client.getGender() : "");
            row.createCell(3).setCellValue(client.getBirthday() != null ? client.getBirthday().toString() : "");
            row.createCell(4).setCellValue(client.getPhoneNumber() != null ? client.getPhoneNumber() : "");
            row.createCell(5).setCellValue(client.getEmail() != null ? client.getEmail() : "");
            row.createCell(6).setCellValue(client.getNationality() != null ? client.getNationality() : "");
            row.createCell(7).setCellValue(client.getIdType() != null ? client.getIdType() : "");
            row.createCell(8).setCellValue(client.getIdNumber() != null ? client.getIdNumber() : "");
            row.createCell(9).setCellValue(client.getIncomeLevel());
            row.createCell(10).setCellValue(client.getRiskLevel());
            if (client.getTotalAssets() != null) {
                row.createCell(11).setCellValue(client.getTotalAssets().doubleValue());
            }
            row.createCell(12).setCellValue(client.getRegisterDate() != null ? client.getRegisterDate().toString() : "");
            row.createCell(13).setCellValue(client.getKycDueDate() != null ? client.getKycDueDate().toString() : "");
            row.createCell(14).setCellValue(client.getStatus() != null ? client.getStatus() : "");
            row.createCell(15).setCellValue(client.getRemarks() != null ? client.getRemarks() : "");
            row.createCell(16).setCellValue(client.getLastUpdated() != null ? client.getLastUpdated().toString() : "");
            row.createCell(17).setCellValue(client.getUserId() != null ? client.getUserId() : "");
        }

        // 调整列宽
        for (int i = 0; i < header.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }
}