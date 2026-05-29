package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.*;
import com.kuzmin.Project_i.repository.AbcAnalyseRepository;
import com.kuzmin.Project_i.repository.AbcXyzMatrixRepository;
import com.kuzmin.Project_i.repository.XyzAnalyseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbcXyzMatrixService {

    private final AbcAnalyseRepository abcAnalyseRepository;
    private final XyzAnalyseRepository xyzAnalyseRepository;
    private final AbcXyzMatrixRepository abcXyzMatrixRepository;

    @Transactional
    public void runAnalysis() {

        abcXyzMatrixRepository.deleteAll();

        List<AbcAnalyse> abcList = abcAnalyseRepository.findAll();

        for (AbcAnalyse abc : abcList) {

            XyzAnalyse xyz =
                    xyzAnalyseRepository
                            .findAll()
                            .stream()
                            .filter(
                                    x ->
                                            x.getCustomer()
                                                    .getId()
                                                    .equals(
                                                            abc.getCustomer()
                                                                    .getId()
                                                    )
                            )
                            .findFirst()
                            .orElse(null);

            if (xyz == null) {
                continue;
            }

            String group =
                    abc.getAbcCategory().name() +
                            xyz.getXyzCategory().name();

            AbcXyzMatrix matrix = new AbcXyzMatrix();

            matrix.setCustomer(abc.getCustomer());

            matrix.setAbcCategory(abc.getAbcCategory());

            matrix.setXyzCategory(xyz.getXyzCategory());

            matrix.setMatrixGroup(group);

            matrix.setRecommendations(buildRecommendation(group));

            abcXyzMatrixRepository.save(matrix);
        }
    }

    private String buildRecommendation(
            String group
    ) {

        return switch (group) {

            case "AX" -> "VIP customers";

            case "AY" -> "Monitor activity";

            case "AZ" -> "Risk group";

            case "BX" -> "Stable customers";

            case "BY" -> "Potential growth";

            case "BZ" -> "Low stability";

            case "CX" -> "Low profit stable";

            case "CY" -> "Weak segment";

            default -> "Unstable low-value customers";
        };
    }
}