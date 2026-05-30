package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Sex;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.repository.*;
import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImportService {

    private final CustomerRepository customerRepository;
    private final UserService userService;

    private final UserRepository userRepository;
    private final DashboardRepository dashboardRepository;
    private final SegmentRepository segmentRepository;

    private final AbcAnalyseRepository abcAnalyseRepository;
    private final XyzAnalyseRepository xyzAnalyseRepository;
    private final RfmAnalyseRepository rfmAnalyseRepository;
    private final AbcXyzMatrixRepository abcXyzMatrixRepository;

    public void importCsv(MultipartFile file, User user) {

        if (file.isEmpty()) {
            throw new RuntimeException("Файл пуст");
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new RuntimeException("Только CSV файлы");
        }

        abcXyzMatrixRepository.deleteAllByUser(user);
        abcAnalyseRepository.deleteAllByUser(user);
        xyzAnalyseRepository.deleteAllByUser(user);
        rfmAnalyseRepository.deleteAllByUser(user);
        dashboardRepository.deleteAllByUser(user);
        segmentRepository.deleteAllByUser(user);
        customerRepository.deleteAllByUser(user);

        user.setLastImportedFileName(
                file.getOriginalFilename()
        );

        userRepository.save(user);

        try (
                Reader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream())
                );

                CSVReader csvReader = new CSVReader(reader)
        ) {

            List<String[]> rows = csvReader.readAll();

            for (int i = 1; i < rows.size(); i++) {

                String[] row = rows.get(i);

                if (row.length != 8) {
                    continue;
                }

                Customer customer = new Customer();

                customer.setAge(
                        Integer.parseInt(row[0])
                );

                customer.setSex(
                        Sex.valueOf(row[1])
                );

                customer.setRegion(
                        row[2]
                );

                customer.setDateOfRegistration(
                        LocalDate.parse(row[3])
                );

                customer.setCountOfOrders(
                        Integer.parseInt(row[4])
                );

                customer.setAverageCheck(
                        BigDecimal.valueOf(
                                Double.parseDouble(row[5])
                        )
                );

                customer.setTotalSpent(
                        BigDecimal.valueOf(
                                Double.parseDouble(row[6])
                        )

                );

                customer.setLastOrderDate(
                        LocalDate.parse(row[7])
                );

                customer.setUser(user);
                customerRepository.save(customer);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка импорта CSV", e);
        }
    }
}