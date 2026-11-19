package CSV;

import Competition.Competition;
import Disciplines.Result;
import User_And_Member.Administrator;
import User_And_Member.Member;
import User_And_Member.Trainer;
import User_And_Member.Treasurer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final CSVMapper csvMapper;

    public FileHandler(CSVMapper csvMapper) {
        this.csvMapper = csvMapper;
    }

    public List<Member> loadMembers(String filePath) throws IOException {
        List<Member> members = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                members.add(csvMapper.fromCSVMember(line));
            }
            return members;
        }
    }

    public void saveMembers(List<Member> members, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Member member : members) {
                writer.write(csvMapper.toCSVMember(member));
                writer.newLine();
            }
        }
    }

    public List<Administrator> loadAdministrator(String filePath) throws IOException {
        List<Administrator> administrator = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                administrator.add(csvMapper.fromCSVAdministrator(line));
            }
        }
        return administrator;
    }

    public void saveAdministrator(List<Administrator> administrator, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Administrator admin : administrator) {
                writer.write(csvMapper.toCSVAdministrator(admin));
                writer.newLine();
            }
        }
    }

    public List<Trainer> loadTrainers(String filePath) throws IOException {
        List<Trainer> trainers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                trainers.add(csvMapper.fromCSVTrainer(line));
            }
        }
        return trainers;
    }

    public void saveTrainers(List<Trainer> trainers, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Trainer trainer : trainers) {
                writer.write(csvMapper.toCSVTrainer(trainer));
                writer.newLine();
            }
        }
    }

    public List<Treasurer> loadTreasurers(String filePath) throws IOException {
        List<Treasurer> treasurers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                treasurers.add(csvMapper.fromCSVTreasurer(line));
            }
        }
        return treasurers;
    }

    public void saveTreasurers(List<Treasurer> treasurers, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Treasurer treasurer : treasurers) {
                writer.write(csvMapper.toCSVTreasurer(treasurer));
                writer.newLine();
            }
        }
    }

    public List<Result> loadResults(String filePath) throws IOException {
        List<Result> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(csvMapper.fromCSVResults(line));
            }
        }
        return results;
    }

    public void saveResults(List<Result> results, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Result result : results) {
                writer.write(csvMapper.toCSVResults(result));
                writer.newLine();
            }
        }
    }

    public List<Competition> loadCompetitions(String filePath) throws IOException {
        List<Competition> competitions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                competitions.add(csvMapper.fromCSVCompetition(line));
            }
        }
        return competitions;
    }

    public void saveCompetitions(List<Competition> competitions, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            for (Competition competition : competitions) {
                writer.write(csvMapper.toCSVCompetition(competition));
                writer.newLine();
            }
        }
    }
}
