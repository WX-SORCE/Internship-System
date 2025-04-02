package com.alxy.clientservice.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ExportService {

    void exportClientsByAdvisorToCSV(ByteArrayOutputStream outputStream, String advisorId) throws IOException;

    void exportClientsByAdvisorToExcel(ByteArrayOutputStream outputStream, String advisorId) throws IOException;
}