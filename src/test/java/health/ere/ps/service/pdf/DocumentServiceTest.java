package health.ere.ps.service.pdf;

import ca.uhn.fhir.context.FhirContext;
import health.ere.ps.model.gematik.BundleWithAccessCodeOrThrowable;
import io.quarkus.test.junit.QuarkusTest;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//@QuarkusTest
public class DocumentServiceTest {

    private final static List<Bundle> testBundles = new ArrayList<>();
    private final static String TARGET_PATH = "target/test_Erezepten/";
    private DocumentService documentService;

    @BeforeAll
    public static void createTestDirectory() throws IOException {
        if (!Path.of(TARGET_PATH).toFile().exists()) {
            Files.createDirectory(Path.of(TARGET_PATH));
        }

        final FhirContext ctx = FhirContext.forR4();

        // GIVEN
        testBundles.add((Bundle) ctx.newXmlParser().parseResource(
                DocumentServiceTest.class.getResourceAsStream("/examples_erezept/Erezept_template_1.xml")));
        testBundles.add((Bundle) ctx.newXmlParser().parseResource(
                DocumentServiceTest.class.getResourceAsStream("/examples_erezept/Erezept_template_2.xml")));
        testBundles.add((Bundle) ctx.newXmlParser().parseResource(
                DocumentServiceTest.class.getResourceAsStream("/examples_erezept/Erezept_template_3.xml")));
        testBundles.add((Bundle) ctx.newXmlParser().parseResource(
                DocumentServiceTest.class.getResourceAsStream("/examples_erezept/Erezept_template_4.xml")));
        testBundles.add((Bundle) ctx.newXmlParser().parseResource(
                DocumentServiceTest.class.getResourceAsStream("/examples_erezept/Erezept_template_5.xml")));
    }

    @BeforeEach
    public void setUp() {
        documentService = new DocumentService();
        documentService.init();
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdf_givenOneMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(1);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_one_medicine.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdf_givenTwoMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(2);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_two_medicines.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdf_givenThreeMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(3);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_three_medicines.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdfWithTwoPages_givenFourMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(4);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_four_medicines.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdfWithTwoPages_givenFiveMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(5);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_five_medicines.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdfWithTwoPages_givenSixMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(6);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_six_medicines.pdf"), baos.toByteArray());
    }

    //TODO: Starting at 7 the QR code on the top-right start being too big, why? How many should we support?

    @Test
    public void generateERezeptPdf_generatesCorrectPdfWithThreePages_givenSevenMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(7);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_seven_medicines.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdfWithThreePages_givenEightMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(8);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_eight_medicines.pdf"), baos.toByteArray());
    }

    @Test
    public void generateERezeptPdf_generatesCorrectPdfWithThreePages_givenNineMedicineToDisplay() throws IOException {
        // WHEN + THEN
        ByteArrayOutputStream baos = getOutputStream(9);
        Files.write(Paths.get(TARGET_PATH + "Erezept_with_nine_medicines.pdf"), baos.toByteArray());
    }


    private ByteArrayOutputStream getOutputStream(int number) {
        List<BundleWithAccessCodeOrThrowable> bundles = new ArrayList<>();
        for (int i=0; i < number; i++) {
            bundles.add(new BundleWithAccessCodeOrThrowable(testBundles.get(i % 5), "MOCK_CODE" + i));
        }
        return documentService.generateERezeptPdf(bundles);
    }
}