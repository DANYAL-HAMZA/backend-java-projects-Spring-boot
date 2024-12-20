package com.example.BankApplication.service;

import com.example.BankApplication.BankApplication.email.dto.EmailDetails;
import com.example.BankApplication.BankApplication.email.service.EmailService;
import com.example.BankApplication.BankApplication.entity.Transaction;
import com.example.BankApplication.BankApplication.entity.User;
import com.example.BankApplication.BankApplication.repository.TransactionRepository;
import com.example.BankApplication.BankApplication.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {
    private TransactionRepository transactionRepository;
    private static final String FILE = "C:\\Users\\pc\\Document\\BankStatement.pdf";
    private UserRepository userRepository;
    private EmailService emailService;

            public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
                LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
                LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

                List<Transaction> transactionList = transactionRepository.findAll().stream().filter(transaction ->
                transaction.getAccountNumber().equals(accountNumber)).filter(transaction ->
                transaction.getCreatedAt().isEqual(start.atStartOfDay())).filter(transaction
                        -> transaction.getCreatedAt().isEqual(end.atStartOfDay())).toList();

                User user = userRepository.findByAccountNumber(accountNumber);
                String customerName = user.getFirstName() + "" + user.getLastName() + "" + user.getOtherName();

                Rectangle statementSize = new Rectangle(PageSize.A4);
                Document document = new Document(statementSize);
                log.info("setting document size");
                OutputStream outputStream = new FileOutputStream(FILE);
                PdfWriter.getInstance(document,outputStream);
                document.open();

                PdfPCell bankName = new PdfPCell(new Phrase("The DANYAL BANK"));
                bankName.setBorder(0);
                bankName.setBackgroundColor(BaseColor.BLUE);
                bankName.setPadding(20f);

                PdfPCell bankAddress = new PdfPCell(new Phrase("Legon, Accra Ghana"));
                bankAddress.setBorder(0);

                PdfPTable bankInfoTable = new PdfPTable(1);
                bankInfoTable.addCell(bankName);
                bankInfoTable.addCell(bankAddress);

                PdfPTable statementInfo = new PdfPTable(2);
                PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date:" + startDate));
                customerInfo.setBorder(0);
                PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
                statement.setBorder(0);
                PdfPCell stopDate = new PdfPCell(new Phrase("End Date:" + endDate));
                stopDate.setBorder(0);
                PdfPCell name = new PdfPCell(new Phrase("Customer Name:" + customerName));
                name.setBorder(0);
                PdfPCell space = new PdfPCell(new Phrase("Customer Name:" + customerName));
                PdfPCell address = new PdfPCell(new Phrase("Customer Address:" + user.getAddress()));
                address.setBorder(0);

                PdfPTable transactionTable = new PdfPTable(4);
                PdfPCell date = new PdfPCell(new Phrase("DATE"));
                date.setBackgroundColor(BaseColor.BLUE);
                date.setBorder(0);

                PdfPCell transactionTYpe = new PdfPCell(new Phrase("TRANSACTION TYPE:" ));
                transactionTYpe.setBackgroundColor(BaseColor.BLUE);
                transactionTYpe.setBorder(0);

                PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
                transactionAmount.setBackgroundColor(BaseColor.BLUE);
                transactionAmount.setBorder(0);

                PdfPCell transactionStatus = new PdfPCell(new Phrase("TRANSACTION STATUS"));
                transactionStatus.setBackgroundColor(BaseColor.BLUE);
                transactionStatus.setBorder(0);

                transactionTable.addCell(date);
                transactionTable.addCell(transactionTYpe);
                transactionTable.addCell(transactionAmount);
                transactionTable.addCell(transactionStatus);

                transactionList.forEach(transaction -> {
                    transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
                    transactionTable.addCell(new Phrase(transaction.getTransactionType().toString()));
                    transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
                    transactionTable.addCell(new Phrase(transaction.getStatus().toString()));

                });
                statementInfo.addCell(customerInfo);
                statementInfo.addCell(statement);
                statementInfo.addCell(endDate);
                statementInfo.addCell(name);
                statementInfo.addCell(space);
                statementInfo.addCell(address);

                document.add(bankInfoTable);
                document.add(statementInfo);
                document.add(transactionTable);

                document.close();

                EmailDetails emailDetails = EmailDetails.builder()
                        .recipient(user.getEmail())
                        .subject("DANYAL BANK STATEMENT OF ACCOUNT")
                        .messageBody("Find your requested account details")
                        .attachment(FILE)
                        .build();
                emailService.sendEmailWithAttachment(emailDetails);






                return transactionList;

            }
}