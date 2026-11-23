import CSV.CSVMapper;
import CSV.FileHandler;
import Competition.Competition;
import Disciplines.CompetitionResult;
import Disciplines.Disciplines;
import Disciplines.Result;
import User_And_Member.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    @TempDir
    Path tempDir;

    @Test
    void testLoadMembers() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        //Act
        List<Member> members = fileHandler.loadMembers("src/CSV/Members.CSV");

        //Assert
        assertFalse(members.isEmpty());
    }

    @Test
    void testSaveMembers() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Path testFile = tempDir.resolve("Members.CSV");

        List<Member> members = new ArrayList<>();
        Member member = new Member("Stein", "Bagger", "stein@mail.dk", "password123", LocalDate.of(1970, 5, 14), 1234, MemberType.COMPETITOR);
        members.add(member);

        //Act
        fileHandler.saveMembers(members, testFile.toString());
        List<Member> loadedMembers = fileHandler.loadMembers(testFile.toString());

        //Assert
        assertTrue(Files.exists(testFile));
        assertEquals(1, loadedMembers.size());
        assertEquals(member.getMemberNumber(), loadedMembers.get(0).getMemberNumber());
        assertEquals("Stein", loadedMembers.get(0).getName());
        assertEquals(member.getLastName(), loadedMembers.get(0).getLastName());
        assertEquals("stein@mail.dk", loadedMembers.get(0).getEmail());
        assertEquals(LocalDate.of(1970, 5, 14), loadedMembers.get(0).getBirthday());
        assertEquals(MemberType.COMPETITOR, loadedMembers.get(0).getMemberType());
    }

    @Test
    void testLoadAdministrator() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        //Act
        List<Administrator> administrator = fileHandler.loadAdministrator("src/CSV/Administrator.CSV");

        //Assert
        assertFalse(administrator.isEmpty());
    }

    @Test
    void testSaveAdministrator() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Path testFile = tempDir.resolve("Administrator.CSV");

        List<Administrator> administrator = new ArrayList<>();
        Administrator admin = new Administrator("stein@mail.dk", "1234");
        administrator.add(admin);

        //Act
        fileHandler.saveAdministrator(administrator, testFile.toString());
        List<Administrator> loadedAdministrator = fileHandler.loadAdministrator(testFile.toString());

        //Assert
        assertTrue(Files.exists(testFile));
        assertEquals(1, loadedAdministrator.size());
        assertEquals("stein@mail.dk", loadedAdministrator.get(0).getEmail());
        assertEquals("1234", loadedAdministrator.get(0).getPassword());
    }

    @Test
    void testLoadTrainers() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        //Act
        List<Trainer> trainers = fileHandler.loadTrainers("src/CSV/Trainers.csv");

        //Assert
        assertFalse(trainers.isEmpty());
    }

    @Test
    void testSaveTrainers() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Path testFile = tempDir.resolve("Trainers.CSV");

        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer = new Trainer("stein@mail.dk", "1234");
        trainers.add(trainer);

        //Act
        fileHandler.saveTrainers(trainers, testFile.toString());
        List<Trainer> loadedTrainers = fileHandler.loadTrainers(testFile.toString());

        //Assert
        assertTrue(Files.exists(testFile));
        assertEquals(1, loadedTrainers.size());
        assertEquals("stein@mail.dk", loadedTrainers.get(0).getEmail());
        assertEquals("1234", loadedTrainers.get(0).getPassword());
    }

    @Test
    void testLoadTreasurers() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        //Act
        List<Treasurer> treasurers = fileHandler.loadTreasurers("src/CSV/Treasurers.CSV");

        //Assert
        assertFalse(treasurers.isEmpty());
    }

    @Test
    void testSaveTreasurers() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Path testFile = tempDir.resolve("Treasurers.CSV");

        List<Treasurer> treasurers = new ArrayList<>();
        Treasurer treasurer = new Treasurer("stein@mail.dk", "1234");
        treasurers.add(treasurer);

        //Act
        fileHandler.saveTreasurers(treasurers, testFile.toString());
        List<Treasurer> loadedTreasurers = fileHandler.loadTreasurers(testFile.toString());

        //Assert
        assertTrue(Files.exists(testFile));
        assertEquals(1, loadedTreasurers.size());
        assertEquals("stein@mail.dk", loadedTreasurers.get(0).getEmail());
        assertEquals("1234", loadedTreasurers.get(0).getPassword());
    }

    @Test
    void testLoadCompetitionResults() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        //Act
        List<CompetitionResult> competitionResults = fileHandler.loadCompetitionResults("src/CSV/CompetitionResults_U18.CSV");

        //Assert
        assertFalse(competitionResults.isEmpty());
    }

    @Test
    void testSaveCompetitionResults() throws IOException {
        //Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Path testFile = tempDir.resolve("CompetitionResults_U18.CSV");

        List<CompetitionResult> competitionResults = new ArrayList<>();
        Result result = new Result(0, 1, 12, LocalDate.of(2026, 11, 18), Disciplines.CRAWL, 1234);
        CompetitionResult competitionResult = new CompetitionResult(1001, result);
        competitionResults.add(competitionResult);

        //Act
        fileHandler.saveCompetitionResults(competitionResults, testFile.toString());
        List<CompetitionResult> loadedCompetitionResults = fileHandler.loadCompetitionResults(testFile.toString());

        //Assert
        assertTrue(Files.exists(testFile));
        assertEquals(1, loadedCompetitionResults.size());
        assertEquals(1001, loadedCompetitionResults.get(0).competitionID());
        assertEquals(0, loadedCompetitionResults.get(0).result().getResultHours());
        assertEquals(1, loadedCompetitionResults.get(0).result().getResultMinutes());
        assertEquals(12, loadedCompetitionResults.get(0).result().getResultSeconds());
        assertEquals(LocalDate.of(2026,11,18), loadedCompetitionResults.get(0).result().getResultDate());
        assertEquals(Disciplines.CRAWL, loadedCompetitionResults.get(0).result().getDiscipline());
        assertEquals(1234, loadedCompetitionResults.get(0).result().getMemberNumber());
    }

    @Test
    void testConnectCompetitionResults() {
        // Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Competition competition1 = new Competition(1001, "Danish Open", "Odense", LocalDate.of(2026, 11, 18));

        Competition competition2 = new Competition(1002, "Junior Cup", "Aarhus", LocalDate.of(2026, 11, 20));

        List<Competition> competitions = new ArrayList<>();
        competitions.add(competition1);
        competitions.add(competition2);

        Result result = new Result(0, 1, 12, LocalDate.of(2026, 11, 18), Disciplines.CRAWL, 1234);

        CompetitionResult competitionResult = new CompetitionResult(1001, result);

        List<CompetitionResult> competitionResults = new ArrayList<>();
        competitionResults.add(competitionResult);

        // Act
        fileHandler.connectCompetitionResults(competitions, competitionResults);

        // Assert
        assertEquals(1, competition1.getResults().size());
        assertEquals(0, competition2.getResults().size());
        assertEquals(1234, competition1.getResults().get(0).getMemberNumber());
        assertEquals(Disciplines.CRAWL, competition1.getResults().get(0).getDiscipline());
    }

    @Test
    void testLoadCompetitionsWithResults() throws IOException {
        // Arrange
        CSVMapper csvMapper = new CSVMapper();
        FileHandler fileHandler = new FileHandler(csvMapper);

        Path competitionsFile = tempDir.resolve("Competitions_U18.CSV");
        Path resultsFile = tempDir.resolve("CompetitionResults_U18.CSV");

        Competition competition1 = new Competition(1001, "Danish Open", "Odense", LocalDate.of(2026, 11, 18));

        Competition competition2 = new Competition(1002, "Junior Cup", "Aarhus", LocalDate.of(2026, 11, 20));

        List<Competition> competitions = new ArrayList<>();
        competitions.add(competition1);
        competitions.add(competition2);

        Result result1 = new Result(0, 1, 12, LocalDate.of(2026, 11, 18), Disciplines.CRAWL, 1234);
        Result result2 = new Result(0, 1, 8, LocalDate.of(2026, 11, 18), Disciplines.CRAWL, 5678);

        List<CompetitionResult> competitionResults = new ArrayList<>();
        competitionResults.add(new CompetitionResult(1001, result1));
        competitionResults.add(new CompetitionResult(1001, result2));

        fileHandler.saveCompetitions(competitions, competitionsFile.toString());
        fileHandler.saveCompetitionResults(competitionResults, resultsFile.toString());

        // Act
        List<Competition> loadedCompetitions = fileHandler.loadCompetitionsWithResults(competitionsFile.toString(), resultsFile.toString());

        // Assert
        assertEquals(2, loadedCompetitions.size());
        Competition loadedCompetition1 = loadedCompetitions.get(0);
        Competition loadedCompetition2 = loadedCompetitions.get(1);
        assertEquals(1001, loadedCompetition1.getCompetitionID());
        assertEquals(2, loadedCompetition1.getResults().size());
        assertEquals(1234, loadedCompetition1.getResults().get(0).getMemberNumber());
        assertEquals(5678, loadedCompetition1.getResults().get(1).getMemberNumber());
        assertEquals(1002, loadedCompetition2.getCompetitionID());
        assertEquals(0, loadedCompetition2.getResults().size());
    }
}
