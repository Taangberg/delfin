import CSV.CSVMapper;
import CSV.FileHandler;
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
        assertEquals(1,loadedAdministrator.size());
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
        fileHandler.saveTrainers(trainers,testFile.toString());
        List<Trainer> loadedTrainers = fileHandler.loadTrainers(testFile.toString());

        //Assert
        assertTrue(Files.exists(testFile));
        assertEquals(1,loadedTrainers.size());
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
        assertEquals(1,loadedTreasurers.size());
        assertEquals("stein@mail.dk", loadedTreasurers.get(0).getEmail());
        assertEquals("1234", loadedTreasurers.get(0).getPassword());
    }
}
